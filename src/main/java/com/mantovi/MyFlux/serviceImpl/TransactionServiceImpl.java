package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.TransactionResponseDTO;
import com.mantovi.MyFlux.mapper.TransactionMapper;
import com.mantovi.MyFlux.model.*;
import com.mantovi.MyFlux.repository.AccountRepository;
import com.mantovi.MyFlux.repository.CategoryRepository;
import com.mantovi.MyFlux.repository.TransactionRepository;
import com.mantovi.MyFlux.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO request, User user) {

        Transaction transaction = transactionMapper.toTransaction(request, user);

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.getType() != request.type()) {
            throw new RuntimeException("Category and transaction type mismatch");
        }

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User not authorized to use this account");
        }

        if (!category.isGlobal() && !category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User not authorized to use this category");
        }

        transaction.setAccount(account);
        transaction.setCategory(category);
        transaction.setStatus(resolveStatus(request.date()));

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(savedTransaction);
    }

    private TransactionStatus resolveStatus(LocalDate date) {

        if (date.isAfter(LocalDate.now())) {
            return TransactionStatus.PENDING;
        }
        return  TransactionStatus.CONFIRMED;
    }
}
