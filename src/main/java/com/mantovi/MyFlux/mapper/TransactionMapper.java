package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.dto.transaction.UpdateTransactionDTO;
import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TransactionMapper {
    public Transaction toTransaction(TransactionRequestDTO request, User user) {
        return Transaction.builder()
                .transactionType(request.type())
                .amount(request.amount())
                .date(request.date())
                .description(request.description())
                .paymentMethodType(request.paymentType())
                .observation(request.observation())
                .status(TransactionStatus.CONFIRMED)
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

                //CONTA
                transaction.getAccount() != null
                    ? transaction.getAccount().getId()
                        : null,

                transaction.getAccount() != null
                    ? transaction.getAccount().getName()
                        : null,

                //CARTÃO
                transaction.getCard() != null
                    ? transaction.getCard().getId()
                        : null,

                transaction.getCard() != null
                    ? transaction.getCard().getName()
                        : null,

                transaction.getCategory().getId(),
                transaction.getCategory().getName(),

                transaction.getPaymentMethodType(),
                transaction.getDescription(),
                transaction.getObservation(),

                transaction.getInstallment() != null,
                transaction.getInstallmentNumber(),

                transaction.getInstallment() != null
                    ? transaction.getInstallment()
                        .getTotalInstallments()
                            : null,


                transaction.getInvoice() != null
                    ? transaction.getInvoice().getId()
                        :null,

                transaction.getCreatedAt()
        );
    }
}
