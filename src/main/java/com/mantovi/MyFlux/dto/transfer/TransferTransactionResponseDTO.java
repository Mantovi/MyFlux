package com.mantovi.MyFlux.dto.transfer;

import com.mantovi.MyFlux.model.PaymentMethodType;
import com.mantovi.MyFlux.model.TransactionStatus;
import com.mantovi.MyFlux.model.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransferTransactionResponseDTO (
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
