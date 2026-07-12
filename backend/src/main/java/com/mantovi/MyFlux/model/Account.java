package com.mantovi.MyFlux.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @NotNull
    @Column(nullable = false, updatable = false, precision = 19, scale = 2)
    private BigDecimal initialBalance;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentBalance;

    @Column(nullable = true)
    private LocalDate openingDate;

    @Column(nullable = false)
    private Boolean active = true;

    private Instant createdAt;
    private Instant updatedAt;

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
    }

    public void credit(BigDecimal amount) {
        validateAmount(amount);

        this.currentBalance = this.currentBalance.add(amount);
    }

    public void debit(BigDecimal amount) {
        validateAmount(amount);

        if (this.currentBalance.compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        this.currentBalance = this.currentBalance.subtract(amount);
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

        if (this.currentBalance == null) {
            this.currentBalance = this.initialBalance;
        }

        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
