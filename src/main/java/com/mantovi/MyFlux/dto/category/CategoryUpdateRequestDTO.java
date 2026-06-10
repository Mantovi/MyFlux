package com.mantovi.MyFlux.dto.category;

import com.mantovi.MyFlux.model.TransactionType;

public record CategoryUpdateRequestDTO(
        String name,
        TransactionType type
) {}
