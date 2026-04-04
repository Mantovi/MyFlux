package com.mantovi.MyFlux.dto;

import com.mantovi.MyFlux.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryRequestDTO (

        @NotBlank
        String name,

        @NotNull
        TransactionType type
) {}