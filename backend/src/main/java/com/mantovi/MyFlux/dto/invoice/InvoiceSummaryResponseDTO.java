package com.mantovi.MyFlux.dto.invoice;

import com.mantovi.MyFlux.model.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public record InvoiceSummaryResponseDTO(
        UUID id,
        UUID cardId,
        String cardName,
        YearMonth referencePeriod,
        LocalDate closingDate,
        LocalDate dueDate,
        InvoiceStatus status,
        BigDecimal totalAmount
) {}
