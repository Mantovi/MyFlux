package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.TransactionResponseDTO;
import com.mantovi.MyFlux.model.User;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO request, User user);
}
