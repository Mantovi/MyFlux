package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.account.AccountRequestDTO;
import com.mantovi.MyFlux.dto.account.AccountResponseDTO;
import com.mantovi.MyFlux.dto.account.AccountUpdateRequestDTO;
import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.User;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponseDTO create(AccountRequestDTO request, User user);

    AccountResponseDTO update(UUID accountId, AccountUpdateRequestDTO request, User user);

    void deleteById(UUID accountId, User user);

    AccountResponseDTO getAccountById(UUID accountId, User user);

    List<AccountResponseDTO> getAllAccounts(User user);

    void applyBalance(Transaction transaction);
}
