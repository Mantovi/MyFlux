package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    Optional<Invoice> findByCardIdAndReferencePeriod(UUID cardId, YearMonth referencePeriod);
}
