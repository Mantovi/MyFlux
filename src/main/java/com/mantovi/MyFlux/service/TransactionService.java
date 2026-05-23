package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.model.User;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDTO createTransaction(TransactionRequestDTO request, User user);
    List<TransactionResponseDTO> findAllFromUser(UUID userId, String description);
}
