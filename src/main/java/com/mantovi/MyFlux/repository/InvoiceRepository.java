package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Invoice;
import com.mantovi.MyFlux.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    Optional<Invoice> findByCardIdAndReferencePeriod(UUID cardId, YearMonth referencePeriod);


    @Query("""
            SELECT DISTINCT i
            FROM Invoice i
            LEFT JOIN FETCH i.transactions t
            LEFT JOIN FETCH i.card
            WHERE i.id = :invoiceId""")
    Optional<Invoice> findByIdWithTransactions(UUID invoiceId);
}
