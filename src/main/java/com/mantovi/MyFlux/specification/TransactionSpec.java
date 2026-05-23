package com.mantovi.MyFlux.specification;

import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TransactionSpec {

    public static Specification<Transaction> belongsToUser(UUID userId) {
        return (root, query, builder) ->
                builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Transaction> descriptionContains(String description) {
        return (root, query, builder) -> {
            if (description == null || description.isEmpty()) {
                return null;
            }
            return builder.like(root.get("description").as(String.class), "%" + description + "%");
        };
    }
}
