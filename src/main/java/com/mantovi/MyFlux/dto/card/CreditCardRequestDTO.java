package com.mantovi.MyFlux.dto.card;

import jakarta.validation.constraints.*;

public record CreditCardRequestDTO(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotNull
        @Min(1)
        @Max(31)
        Integer closingDay,

        @NotNull
        @Min(1)
        @Max(31)
        Integer dueDay
) {}
