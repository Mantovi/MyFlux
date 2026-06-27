package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.account.AccountRequestDTO;
import com.mantovi.MyFlux.dto.account.AccountResponseDTO;
import com.mantovi.MyFlux.dto.account.AccountUpdateRequestDTO;
import com.mantovi.MyFlux.mapper.AccountMapper;
import com.mantovi.MyFlux.model.*;
import com.mantovi.MyFlux.repository.AccountRepository;
import com.mantovi.MyFlux.repository.TransactionRepository;
import com.mantovi.MyFlux.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    public final AccountRepository accountRepository;
    public final AccountMapper accountMapper;
    public final TransactionRepository transactionRepository;

    @Override
    public AccountResponseDTO create(AccountRequestDTO request, User user) {
        Account account = accountMapper.toAccount(request, user);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toAccountResponse(savedAccount);
    }

    @Override
    public AccountResponseDTO update(UUID accountId, AccountUpdateRequestDTO request, User user) {

        Account account = accountRepository.findById(accountId)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        accountMapper.updateAccount(account, request, user);
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toAccountResponse(savedAccount);
    }

    @Override
    public void deleteById(UUID accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        if (transactionRepository.existsByAccountId(accountId)) {
            throw new RuntimeException("Não é possível excluir contas que possuem transações cadastradas");
        }

        if (account.getActive().equals(true)) {
            throw new RuntimeException("Essa conta está ativa, desative essa conta antes de excluir");
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    public AccountResponseDTO getAccountById(UUID accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        return accountMapper.toAccountResponse(account);
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts(User user) {
        if (accountRepository.count() == 0) {
            throw new RuntimeException("Nenhum conta encontrada");
        }
        return accountRepository.findByUserId(user.getId())
                .stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    public void applyBalance(Transaction transaction) {
        applyBalanceEffect(transaction);
    }

    private void applyBalanceEffect(Transaction transaction) {
        if (transaction.getStatus() != TransactionStatus.CONFIRMED) {
            return;
        }

        if (transaction.getAccount() == null) {
            return;
        }

        Account account = transaction.getAccount();

        BigDecimal currentBalance = account.getCurrentBalance();

        if (transaction.getTransactionType() == TransactionType.INCOME){
            account.setCurrentBalance(currentBalance.add(transaction.getAmount()
                    )
            );
        } else {
            account.setCurrentBalance(currentBalance.subtract(transaction.getAmount()
                    )
            );
        }
        accountRepository.save(account);
    }
}
