package com.mantovi.MyFlux.dto;

import com.mantovi.MyFlux.model.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record AccountResponseDTO(
        UUID id,
        String name,
        AccountType accountType,
        BigDecimal openingBalance,
        LocalDate openingDate,
        UserResponseDTO user
) {}
