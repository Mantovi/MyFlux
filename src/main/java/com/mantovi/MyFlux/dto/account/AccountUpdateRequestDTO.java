package com.mantovi.MyFlux.dto.account;

import com.mantovi.MyFlux.model.AccountType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountUpdateRequestDTO(
        String name,
        AccountType accountType,
        BigDecimal initialBalance,
        BigDecimal currentBalance,
        LocalDate openingDate,
        Boolean active
) {}
