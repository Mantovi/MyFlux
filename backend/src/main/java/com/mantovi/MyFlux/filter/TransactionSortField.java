package com.mantovi.MyFlux.filter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionSortField {
    DATE("date"),
    AMOUNT("amount"),
    DESCRIPTION("description");

    private final String field;
}
