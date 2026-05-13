package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.model.User;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO request, User user);
}
