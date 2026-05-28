package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.invoice.InvoiceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    InvoiceResponseDTO findById(UUID invoiceId);
}
