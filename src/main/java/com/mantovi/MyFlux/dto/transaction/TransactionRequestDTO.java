package com.mantovi.MyFlux.dto.transaction;

import com.mantovi.MyFlux.model.PaymentMethodType;
import com.mantovi.MyFlux.model.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionRequestDTO(
        @NotNull
        TransactionType type,

        @DecimalMin(value = "0.01")
        @NotNull
        BigDecimal amount,

        @NotNull
        LocalDate date,

        @NotNull
        PaymentMethodType paymentType,

        @NotNull
        UUID accountId,

        @NotNull
        UUID categoryId,

        @NotBlank
        @Size(min = 1, max = 100)
        String description,

        @Size(max = 500)
        String observation
) {}
