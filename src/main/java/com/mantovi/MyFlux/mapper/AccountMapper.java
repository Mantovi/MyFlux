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
                .openingBalance(request.openingBalance())
                .openingDate(request.openingDate())
                .user(user)
                .build();
    }

    public AccountResponseDTO  toAccountResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getName(),
                account.getAccountType(),
                account.getOpeningBalance(),
                account.getOpeningDate(),
                userMapper.toResponseUser(account.getUser())
        );
    }
}
