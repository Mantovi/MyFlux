package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.AccountRequestDTO;
import com.mantovi.MyFlux.dto.AccountResponseDTO;
import com.mantovi.MyFlux.model.Account;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private final UserMapper userMapper =  new UserMapper();

    public Account toAccount(AccountRequestDTO request, User user) {
        return Account.builder()
                .name(request.name())
                .accountType(request.accountType())
                .initialBalance(request.initialBalance())
                .openingDate(request.openingDate())
                .user(user)
                .build();
    }

    public AccountResponseDTO  toAccountResponse(Account account) {
        return new AccountResponseDTO(
                account.getName(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getCurrentBalance(),
                account.getOpeningDate(),
                userMapper.toResponseUser(account.getUser())
        );
    }
}
