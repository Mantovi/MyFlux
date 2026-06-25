package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.invoice.InvoiceResponseDTO;
import com.mantovi.MyFlux.dto.invoice.InvoiceSummaryResponseDTO;
import com.mantovi.MyFlux.mapper.InvoiceMapper;
import com.mantovi.MyFlux.model.Invoice;
import com.mantovi.MyFlux.repository.InvoiceRepository;
import com.mantovi.MyFlux.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public InvoiceResponseDTO findById(UUID invoiceId) {
        Invoice invoice = invoiceRepository.findByIdWithTransactions(invoiceId)
                .orElseThrow(() -> new RuntimeException("Fatura não encontrada"));

        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public List<InvoiceSummaryResponseDTO> listInvoicesByCard(UUID cardId, YearMonth referencePeriod) {
        if (referencePeriod != null) {
            return invoiceRepository.findInvoiceByCardIdAndReferencePeriod(cardId, referencePeriod)
                    .stream()
                    .map(invoiceMapper::toSummaryResponse)
                    .toList();
        }

        return invoiceRepository.findInvoicesByCardId(cardId)
                .stream()
                .map(invoiceMapper::toSummaryResponse)
                .toList();
    }
}
