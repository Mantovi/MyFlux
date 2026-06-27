package com.mantovi.MyFlux.factory;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.mapper.TransactionMapper;
import com.mantovi.MyFlux.model.*;
import com.mantovi.MyFlux.resolver.InvoiceResolver;
import com.mantovi.MyFlux.resolver.AccountResolver;
import com.mantovi.MyFlux.resolver.CreditCardResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionFactory {

    private final InvoiceResolver invoiceResolver;
    private final TransactionMapper transactionMapper;
    private final AccountResolver accountResolver;
    private final CreditCardResolver creditCardResolver;

    public Transaction createSingleTransaction(
            TransactionRequestDTO request, User user, Category category, Installment installment, Integer installmentNumber) {
        return buildSingleTransaction(request, user, category, installment, installmentNumber);
    }

    //==================================================================================================================
    //TRANSAÇÃO NORMAL
    //==================================================================================================================

    private Transaction buildSingleTransaction(
            TransactionRequestDTO request, User user, Category category, Installment installment, Integer installmentNumber) {

        Transaction transaction = transactionMapper.toTransaction(request, user);

        transaction.setCategory(category);
        transaction.setStatus(resolveStatus(request.date()));
        transaction.setInstallment(installment);
        transaction.setInstallmentNumber(installmentNumber);

        //==================================================================================================================
        //TRANSAÇÃO DE CONTA - TUDO QUE SAI DIRETO DA CONTA NO MOMENTO EM QUE A TRANSAÇÃO É EFETUADA.
        //==================================================================================================================

        if (request.accountId() != null) {
            Account account = accountResolver.validateAccount(
                    request.accountId(),
                    user
            );

            transaction.setAccount(account);
        }

        //==================================================================================================================
        //TRANSAÇÃO DE CARTÃO
        //==================================================================================================================

        if (request.cardId() != null) {
            CreditCard card = creditCardResolver.validateCreditCard(
                    request.cardId(),
                    user
            );

            Invoice invoice = invoiceResolver.findOrCreate(
                    card,
                    request.date()
            );
            transaction.setCard(card);
            transaction.setInvoice(invoice);
        }
        return transaction;
    }


    //==================================================================================================================
    //STATUS DA TRANSAÇÃO POR DATA
    //==================================================================================================================

    private TransactionStatus resolveStatus(LocalDate date){
        if (date.isAfter(LocalDate.now())) {
            return TransactionStatus.PENDING;
        }
        return TransactionStatus.CONFIRMED;
    }
}