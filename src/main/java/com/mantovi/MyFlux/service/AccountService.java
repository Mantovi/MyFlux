package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.account.AccountRequestDTO;
import com.mantovi.MyFlux.dto.account.AccountResponseDTO;
import com.mantovi.MyFlux.model.User;

public interface AccountService {
    AccountResponseDTO create(AccountRequestDTO request, User user);
}
