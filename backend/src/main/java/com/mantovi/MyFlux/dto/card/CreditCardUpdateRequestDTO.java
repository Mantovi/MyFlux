package com.mantovi.MyFlux.dto.card;

public record CreditCardUpdateRequestDTO (
        String name,
        Integer closingDay,
        Integer dueDay,
        Boolean active
) {}
