package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.account.AccountRequestDTO;
import com.mantovi.MyFlux.dto.account.AccountResponseDTO;
import com.mantovi.MyFlux.dto.account.AccountUpdateRequestDTO;
import com.mantovi.MyFlux.model.Account;
import com.mantovi.MyFlux.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final UserMapper userMapper;

    public Account toAccount(AccountRequestDTO request, User user) {
        return Account.builder()
                .name(request.name())
                .accountType(request.accountType())
                .initialBalance(request.initialBalance())
                .openingDate(request.openingDate())
                .user(user)
                .build();
    }

    public void updateAccount(Account account, AccountUpdateRequestDTO request, User user) {
        if (request.name() != null) {
            account.setName(request.name());
        }
        if (request.accountType() != null) {
            account.setAccountType(request.accountType());
        }
        if (request.initialBalance() != null) {
            account.setInitialBalance(request.initialBalance());
        }
        if (request.currentBalance() != null) {
            account.setCurrentBalance(request.currentBalance());
        }
        if (request.openingDate() != null) {
            account.setOpeningDate(request.openingDate());
        }
        if (request.active() != null) {
            account.setActive(request.active());
        }
        if (user != null) {
            account.setUser(user);
        }
    }

    public AccountResponseDTO  toAccountResponse(Account account) {
        return new AccountResponseDTO(
                account.getName(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getCurrentBalance(),
                account.getOpeningDate(),
                account.getActive(),
                userMapper.toResponseUser(account.getUser())
        );
    }
}
