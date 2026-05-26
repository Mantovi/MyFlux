package com.mantovi.MyFlux.specification;

import com.mantovi.MyFlux.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TransactionSpec {

    public static Specification<Transaction> belongsToUser(UUID userId) {
        return (root, query, builder) ->
                builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Transaction> descriptionContains(String description) {
        return (root, query, builder) -> {
            if (description == null || description.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(
                    root.get("description")), "%" + description.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Transaction> typeEquals(TransactionType transactionType) {
        return (root, query, builder) -> {
            if (transactionType == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("transactionType"), transactionType);
        };
    }

    public static Specification<Transaction> paymentMethodUsed(PaymentMethodType paymentMethodType) {
        return (root, query, builder) -> {
            if (paymentMethodType == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("paymentMethodType"), paymentMethodType);
        };
    }

    public static Specification<Transaction> statusTransaction(TransactionStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<Transaction> transactionDate(LocalDate date) {
        return (root, query, builder) -> {
            if (date == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("date"), date);
        };
    }

    public static Specification<Transaction> valueRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return (root, query, builder) -> {
            if (minAmount == null && maxAmount == null) {
                return builder.conjunction();
            }
            if(minAmount != null && maxAmount != null) {
                return builder.between(root.get("amount"), minAmount, maxAmount);
            }
            if(minAmount != null){
                return builder.greaterThanOrEqualTo(root.get("amount"), minAmount);
            }
            return builder.lessThanOrEqualTo(root.get("amount"), maxAmount);
        };
    }

    public static Specification<Transaction> belongsToCategory(UUID categoryId) {
        return (root, query, builder) -> {
            if (categoryId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<Transaction> belongsToAccount(UUID accountId) {
        return (root, query, builder) -> {
            if (accountId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("account").get("id"), accountId);
        };
    }

    public static Specification<Transaction> belongsToCard(UUID cardId) {
        return (root, query, builder) -> {
            if (cardId == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("card").get("id"), cardId);
        };
    }
}
