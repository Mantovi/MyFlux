package com.mantovi.MyFlux.specification;

import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.TransactionType;
import org.springframework.data.jpa.domain.Specification;

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
}
