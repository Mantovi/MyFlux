package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.AccountRequestDTO;
import com.mantovi.MyFlux.dto.AccountResponseDTO;
import com.mantovi.MyFlux.mapper.AccountMapper;
import com.mantovi.MyFlux.model.Account;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.AccountRepository;
import com.mantovi.MyFlux.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
