package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.factory.TransactionFactory;
import com.mantovi.MyFlux.filter.TransactionFilterDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.filter.TransactionSortField;
import com.mantovi.MyFlux.mapper.TransactionMapper;
import com.mantovi.MyFlux.model.*;
import com.mantovi.MyFlux.repository.*;
import com.mantovi.MyFlux.service.AccountService;
import com.mantovi.MyFlux.service.InstallmentService;
import com.mantovi.MyFlux.service.TransactionService;
import com.mantovi.MyFlux.specification.TransactionSpec;
import com.mantovi.MyFlux.resolver.CategoryResolver;
import com.mantovi.MyFlux.validator.TransactionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final InstallmentService installmentService;
    private final AccountService accountService;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final TransactionValidator transactionValidator;
    private final CategoryResolver categoryResolver;

    private final TransactionFactory transactionFactory;

    @Override
    public List<TransactionResponseDTO> findTransactionsFromUser(UUID userId, TransactionFilterDTO filters) {

        Specification<Transaction> specification = Specification
                .where(TransactionSpec.belongsToUser(userId))
                .and(TransactionSpec.descriptionContains(filters.description()))
                .and(TransactionSpec.typeEquals(filters.transactionType()))
                .and(TransactionSpec.paymentMethodUsed(filters.paymentMethodType()))
                .and(TransactionSpec.statusTransaction(filters.status()))
                .and(TransactionSpec.transactionDate(filters.date()))
                .and(TransactionSpec.transactionBetweenDates(filters.startDate(), filters.endDate()))
                .and(TransactionSpec.valueRange(filters.minAmount(), filters.maxAmount()))
                .and(TransactionSpec.belongsToCategory(filters.categoryId()))
                .and(TransactionSpec.belongsToAccount(filters.accountId()))
                .and(TransactionSpec.belongsToCard(filters.cardId()));

        Sort sort = Sort.by(filters.direction() != null ? filters.direction() : Sort.Direction.ASC,
                filters.sortBy() != null ? filters.sortBy().getField() : TransactionSortField.DATE.getField()
        );

        List<Transaction> transactions = transactionRepository.findAll(specification, sort);

        return transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO request, User user) {

        transactionValidator.validateTransaction(request);

        Category category = categoryResolver.validateCategory(
                request.categoryId(),
                request.type(),
                user
        );

        if (transactionValidator.isInstallment(request)){
            Transaction firstTransaction = installmentService.createInstallmentTransactions(
                    request, user, category
            );
            return transactionMapper.toTransactionResponse(firstTransaction);
        }

        Transaction transaction = transactionFactory.createSingleTransaction(
                request, user, category, null, null
        );
        transactionRepository.save(transaction);
        accountService.applyBalance(transaction);
        return transactionMapper.toTransactionResponse(transaction);
    }
}