package com.mantovi.MyFlux.dto.category;

import com.mantovi.MyFlux.model.TransactionType;

import java.util.UUID;

public record CategoryResponseDTO (
        UUID id,
        String name,
        TransactionType type,
        boolean isGlobal
) {}