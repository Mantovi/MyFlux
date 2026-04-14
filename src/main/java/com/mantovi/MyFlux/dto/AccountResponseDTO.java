package com.mantovi.MyFlux.dto;

import com.mantovi.MyFlux.model.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountResponseDTO(
        String name,
        AccountType accountType,
        BigDecimal openingBalance,
        LocalDate openingDate,
        UserResponseDTO user
) {}
