package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.dto.transfer.TransferRequestDTO;
import com.mantovi.MyFlux.dto.transfer.TransferResponseDTO;
import com.mantovi.MyFlux.dto.transfer.TransferTransactionResponseDTO;
import com.mantovi.MyFlux.model.Account;
import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.Transfer;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {
    public Transfer toTransfer(TransferRequestDTO request, User user, Account sourceAccount, Account destinationAccount) {
        return Transfer.builder()
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .user(user)
                .transferAmount(request.transferAmount())
                .transferDate(request.transferDate())
                .observation(request.observation())
                .build();
    }

    public TransferResponseDTO toTransferResponse(Transfer transfer) {
        return new TransferResponseDTO(
                transfer.getId(),
                transfer.getTransferAmount(),
                transfer.getTransferDate(),
                transfer.getObservation(),
                transfer.getSourceAccount().getId(),
                transfer.getSourceAccount().getName(),
                transfer.getDestinationAccount().getId(),
                transfer.getDestinationAccount().getName(),
                toTransactionDTO(transfer.getExpenseTransaction()),
                toTransactionDTO(transfer.getIncomeTransaction()),
                transfer.getCreatedAt()
        );
    }

    private TransferTransactionResponseDTO toTransactionDTO(Transaction transaction) {
        if (transaction == null) return null;

        return new TransferTransactionResponseDTO(
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
