package com.mantovi.MyFlux.dto;

import com.mantovi.MyFlux.model.PaymentMethodType;
import com.mantovi.MyFlux.model.TransactionStatus;
import com.mantovi.MyFlux.model.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        TransactionType transactionType,
        BigDecimal amount,
        LocalDate date,
        TransactionStatus status,
        UUID accountId,
        String accountName,
        UUID categoryId,
        String categoryName,
        PaymentMethodType paymentMethodType,
        String description,
        String observation,
        Instant createdAt
) {}
