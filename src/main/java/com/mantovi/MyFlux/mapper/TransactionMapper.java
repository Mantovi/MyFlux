package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.dto.transaction.UpdateTransactionDTO;
import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction toTransaction(TransactionRequestDTO request, User user) {
        return Transaction.builder()
                .transactionType(request.type())
                .amount(request.amount())
                .date(request.date())
                .paymentMethodType(request.paymentType())
                .description(request.description())
                .observation(request.observation())
                .user(user)
                .build();
    }

    public TransactionResponseDTO toTransactionResponse(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getStatus(),
                transaction.getAccount().getId(),
                transaction.getAccount().getName(),
                transaction.getCategory().getId(),
                transaction.getCategory().getName(),
                transaction.getPaymentMethodType(),
                transaction.getDescription(),
                transaction.getObservation(),
                transaction.getCreatedAt()
        );
    }
}
