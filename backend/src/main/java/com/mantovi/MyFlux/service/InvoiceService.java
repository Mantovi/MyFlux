package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.invoice.InvoiceResponseDTO;
import com.mantovi.MyFlux.dto.invoice.InvoiceSummaryResponseDTO;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    InvoiceResponseDTO findById(UUID invoiceId);

    List<InvoiceSummaryResponseDTO> listInvoicesByCard(UUID cardId, YearMonth referencePeriod);
}
