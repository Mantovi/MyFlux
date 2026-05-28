package com.mantovi.MyFlux.model;

import com.mantovi.MyFlux.config.YearMonthAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = {"card_id", "reference_period"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CreditCard card;

    @NotNull
    @Column(nullable = false)
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth referencePeriod;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate closingDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate dueDate;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }
}