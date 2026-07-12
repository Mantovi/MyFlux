package com.mantovi.MyFlux.dto.transfer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransferRequestDTO(
        @NotNull
        UUID sourceAccountId,

        @NotNull
        UUID destinationAccountId,

        @NotNull
        @Positive
        BigDecimal transferAmount,

        @NotNull
        LocalDate transferDate,

        @Size(max = 500)
        String observation
) {}