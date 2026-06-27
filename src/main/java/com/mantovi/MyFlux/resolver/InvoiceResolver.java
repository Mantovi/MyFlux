package com.mantovi.MyFlux.resolver;

import com.mantovi.MyFlux.model.CreditCard;
import com.mantovi.MyFlux.model.Invoice;
import com.mantovi.MyFlux.model.InvoiceStatus;
import com.mantovi.MyFlux.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class InvoiceResolver {

    private final InvoiceRepository invoiceRepository;

    public Invoice findOrCreate(CreditCard card, LocalDate transactionDate) {
        return findOrCreateInvoice(card, transactionDate);
    }


    private Invoice findOrCreateInvoice(CreditCard card, LocalDate transactionDate) {

        YearMonth referencePeriod = resolveInvoicePeriod(
                transactionDate, card.getClosingDay()
        );

        return invoiceRepository.findInvoiceByCardIdAndReferencePeriod(
                        card.getId(),
                        referencePeriod
                )
                .orElseGet(() -> {

                    LocalDate closingDate = LocalDate.of(
                            referencePeriod.getYear(), referencePeriod.getMonth(), card.getClosingDay()
                    );

                    LocalDate dueDate = LocalDate.of(
                            referencePeriod.plusMonths(1).getYear(),
                            referencePeriod.plusMonths(1).getMonth(),
                            card.getDueDay()
                    );

                    Invoice invoice = Invoice.builder()
                            .card(card)
                            .referencePeriod(referencePeriod)
                            .startDate(closingDate.minusMonths(1).plusDays(1))
                            .closingDate(closingDate)
                            .dueDate(dueDate)
                            .status(InvoiceStatus.OPEN)
                            .user(card.getUser())
                            .build();

                    return invoiceRepository.save(invoice);
                });
    }

    private YearMonth resolveInvoicePeriod(LocalDate transactionDate, Integer closingDay) {
        if (transactionDate.getDayOfMonth() > closingDay) {
            return YearMonth.from(transactionDate.plusMonths(1));
        }
        return YearMonth.from(transactionDate);
    }
}
