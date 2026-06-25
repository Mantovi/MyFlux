package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.invoice.InvoiceResponseDTO;
import com.mantovi.MyFlux.dto.invoice.InvoiceSummaryResponseDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionInvoiceResponseDTO;
import com.mantovi.MyFlux.model.Invoice;
import com.mantovi.MyFlux.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InvoiceMapper {

    private final TransactionMapper transactionMapper;

    public InvoiceMapper(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public InvoiceResponseDTO toResponseDTO(Invoice invoice) {

        List<TransactionInvoiceResponseDTO> transactions =
                invoice.getTransactions()
                        .stream()
                        .map(transactionMapper::toInvoiceResponse)
                        .toList();

        BigDecimal totalAmount = invoice.getTransactions()
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new InvoiceResponseDTO(
                invoice.getId(),
                invoice.getCard().getId(),
                invoice.getCard().getName(),
                invoice.getReferencePeriod(),
                invoice.getStartDate(),
                invoice.getClosingDate(),
                invoice.getDueDate(),
                invoice.getStatus(),
                totalAmount,
                transactions
        );
    }

    public InvoiceSummaryResponseDTO toSummaryResponse(Invoice invoice) {

        BigDecimal totalAmount = invoice.getTransactions()
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new InvoiceSummaryResponseDTO(
                invoice.getId(),
                invoice.getCard().getId(),
                invoice.getCard().getName(),
                invoice.getReferencePeriod(),
                invoice.getClosingDate(),
                invoice.getDueDate(),
                invoice.getStatus(),
                totalAmount
        );
    }
}
