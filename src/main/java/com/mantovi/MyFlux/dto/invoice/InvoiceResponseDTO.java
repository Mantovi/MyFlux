package com.mantovi.MyFlux.dto.invoice;

import com.mantovi.MyFlux.dto.transaction.TransactionInvoiceResponseDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.model.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public record InvoiceResponseDTO(
        UUID id,
        UUID cardId,
        String cardName,
        YearMonth referencePeriod,
        LocalDate startDate,
        LocalDate closingDate,
        LocalDate dueDate,
        InvoiceStatus status,
        BigDecimal totalAmount,
        List<TransactionInvoiceResponseDTO> transactions
) {}
