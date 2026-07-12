package com.mantovi.MyFlux.validator;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.model.PaymentMethodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionValidator {

    public void validateTransaction(TransactionRequestDTO request) {
        validateTransactionSource(request);
        validateInstallment(request);
    }

    private void validateTransactionSource(TransactionRequestDTO request) {
        boolean hasAccount = request.accountId() != null;
        boolean hasCard = request.cardId() != null;

        if (hasAccount == hasCard) {
            throw new RuntimeException("Uma transação deve vir de uma conta OU de um cartão");
        }
    }

    private void validateInstallment(TransactionRequestDTO request) {
        if (request.totalInstallments() != null
                && request.totalInstallments() > 1
                && request.paymentType() != PaymentMethodType.CREDIT_CARD) {

            throw new RuntimeException("Parcelamentos só podem ser feitos utilizando um cartão de crédito");
        }
    }

    public boolean isInstallment(TransactionRequestDTO request) {
        return request.totalInstallments() != null && request.totalInstallments() > 1;
    }
}
