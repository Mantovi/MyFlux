package com.mantovi.MyFlux.resolver;

import com.mantovi.MyFlux.model.Account;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountResolver {

    private final AccountRepository accountRepository;

    public Account validateAccount(UUID accountId, User user) {
        return findAndValidateAccount(accountId, user);
    }

    private Account findAndValidateAccount(UUID accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Usuário sem acesso a essa conta");
        }
        return account;
    }
}
