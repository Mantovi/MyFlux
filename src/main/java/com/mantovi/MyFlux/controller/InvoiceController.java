package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.invoice.InvoiceResponseDTO;
import com.mantovi.MyFlux.dto.invoice.InvoiceSummaryResponseDTO;
import com.mantovi.MyFlux.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(invoiceService.findById(id));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<InvoiceSummaryResponseDTO>> findByCardId(@PathVariable UUID cardId,
                                                                        @RequestParam(required = false) YearMonth referencePeriod) {
        return ResponseEntity.ok(invoiceService.listInvoicesByCard(cardId, referencePeriod));
    }
}
