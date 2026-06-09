package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.account.AccountRequestDTO;
import com.mantovi.MyFlux.dto.account.AccountResponseDTO;
import com.mantovi.MyFlux.dto.account.AccountUpdateRequestDTO;
import com.mantovi.MyFlux.mapper.AccountMapper;
import com.mantovi.MyFlux.model.Account;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.AccountRepository;
import com.mantovi.MyFlux.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    public final AccountRepository accountRepository;
    public final AccountMapper  accountMapper;

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
}
