package com.mantovi.MyFlux.dto.transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransferResponseDTO(
        UUID id,
        BigDecimal transferAmount,
        LocalDate transferDate,
        String observation,
        UUID sourceAccountId,
        String sourceAccountName,
        UUID destinationAccountId,
        String destinationAccountName,
        TransferTransactionResponseDTO expenseTransactionId,
        TransferTransactionResponseDTO incomeTransactionId,
        Instant createdAt
) {}
