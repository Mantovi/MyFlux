package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.factory.TransactionFactory;
import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.Installment;
import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.InstallmentRepository;
import com.mantovi.MyFlux.repository.TransactionRepository;
import com.mantovi.MyFlux.service.InstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InstallmentRepository installmentRepository;
    private final TransactionRepository transactionRepository;

    private final TransactionFactory transactionFactory;

    @Override
    public Transaction createInstallmentTransactions(TransactionRequestDTO request, User user, Category category) {
        return buildInstallmentTransactions(request, user, category);
    }

    private Transaction buildInstallmentTransactions(TransactionRequestDTO request, User user, Category category) {
        Installment installment = Installment.builder()
                .description(request.description())
                .totalInstallments(request.totalInstallments())
                .user(user)
                .build();

        installmentRepository.save(installment);

        BigDecimal installmentValue = request.amount()
                .divide(BigDecimal.valueOf(request.totalInstallments()),
                        2, RoundingMode.HALF_UP);

        Transaction firstTransaction = null;

        for(int i = 1; i <= request.totalInstallments(); i++) {
            LocalDate installmentDate = request.date().plusMonths(i-1);

            TransactionRequestDTO installmentRequest =
                    new TransactionRequestDTO(
                            request.type(),
                            installmentValue,
                            installmentDate,
                            request.description(),
                            request.paymentType(),
                            request.accountId(),
                            request.cardId(),
                            null,
                            null,
                            request.categoryId(),
                            request.observation()
                    );

            Transaction transaction = transactionFactory.createSingleTransaction(
                    installmentRequest, user, category, installment, i
            );

            transactionRepository.save(transaction);

            if (i == 1) {
                firstTransaction = transaction;
            }
        }
        return firstTransaction;
    }
}
