package com.mantovi.MyFlux.dto.transaction;

import com.mantovi.MyFlux.model.TransactionStatus;
import com.mantovi.MyFlux.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionInvoiceResponseDTO(
        UUID id,
        String description,
        String observation,
        BigDecimal amount,
        LocalDate date,
        TransactionStatus status,
        TransactionType transactionType,
        Integer installmentNumber
) {}