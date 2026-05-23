package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.dto.transaction.TransactionResponseDTO;
import com.mantovi.MyFlux.mapper.TransactionMapper;
import com.mantovi.MyFlux.model.*;
import com.mantovi.MyFlux.repository.*;
import com.mantovi.MyFlux.service.TransactionService;
import com.mantovi.MyFlux.specification.TransactionSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private final AccountRepository accountRepository;
    private final CreditCardRepository creditCardRepository;
    private final CategoryRepository categoryRepository;
    private final InvoiceRepository invoiceRepository;
    private final InstallmentRepository installmentRepository;

    @Override
    public List<TransactionResponseDTO> findAllFromUser(UUID userId, String description) {

        Specification<Transaction> specification = Specification
                .where(TransactionSpec.belongsToUser(userId))
                .and(TransactionSpec.descriptionContains(description));

        List<Transaction> transactions = transactionRepository.findAll(specification);

        return transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO request, User user) {

        validateTransactionSource(request);

        validateInstallment(request);

        Category category = findAndValidateCategory(
                request.categoryId(),
                request.type(),
                user
        );

        //Parcelamento
        if (isInstallment(request)){
            Transaction firstTransaction = createInstallmentTransactions(
                    request, user, category
            );
            return transactionMapper.toTransactionResponse(firstTransaction);
        }

        //Transação Normal
        Transaction transaction = buildSingleTransaction(
                request, user, category, null, null
        );
        transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(transaction);

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
            Account account = findAndValidateAccount(
                    request.accountId(),
                    user
            );

            transaction.setAccount(account);
            applyBalanceEffect(transaction);
        }

    //==================================================================================================================
    //TRANSAÇÃO DE CARTÃO
    //==================================================================================================================


        if (request.cardId() != null) {
            CreditCard card = findAndValidateCreditCard(
                    request.cardId(),
                    user
            );

            Invoice invoice = findOrCreateInvoice(
                    card,
                    request.date()
            );
            transaction.setCard(card);
            transaction.setInvoice(invoice);
        }
        return transaction;
    }

    //==================================================================================================================
    //PARCELAMENTO
    //==================================================================================================================

    private Transaction createInstallmentTransactions(TransactionRequestDTO request, User user, Category category) {
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

            Transaction transaction = buildSingleTransaction(
                    installmentRequest, user, category, installment, i
            );

            transactionRepository.save(transaction);

            if (i == 1) {
                firstTransaction = transaction;
            }
        }
        return firstTransaction;
    }

    //==================================================================================================================
    //VALIDAÇÕES
    //==================================================================================================================

    private void validateTransactionSource(TransactionRequestDTO request) {
        boolean hasAccount = request.accountId() != null;
        boolean hasCard = request.cardId() != null;

        if (hasAccount == hasCard) {
            throw new RuntimeException("Uma transação deve vir de uma conta OU de um cartão");
        }
    }

    //==================================================================================================================

    private void validateInstallment(TransactionRequestDTO request) {
        if (request.totalInstallments() != null
                && request.totalInstallments() > 1
                    && request.paymentType() != PaymentMethodType.CREDIT_CARD) {

            throw new RuntimeException("Parcelamentos só podem ser feitos utilizando um cartão de crédito");
        }
    }

    //==================================================================================================================

    private boolean isInstallment(TransactionRequestDTO request) {
        return request.totalInstallments() != null && request.totalInstallments() > 1;
    }

    //==================================================================================================================
    //ENCONTRAR E VALIDAR
    //==================================================================================================================

    private Account findAndValidateAccount(UUID accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Usuário sem acesso a essa conta");
        }
        return account;
    }

    //==================================================================================================================

    private CreditCard findAndValidateCreditCard(UUID cardId, User user) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        if (!card.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Usuário sem acesso a esse cartão");
        }
        return card;
    }

    //==================================================================================================================

    private Category findAndValidateCategory(UUID categoryId, TransactionType type, User user) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Usuário sem acesso a essa categoria");
        }

        if (category.getType() != type) {
            throw new RuntimeException("O tipo da categoria e da transação não coincidem");
        }

        if (!category.isGlobal() &&
                (category.getUser() == null ||
                        !category.getUser().getId().equals(user.getId())
                )) {
            throw new RuntimeException("Usuário sem acesso a essa categoria");
        }

        return category;
    }

    //==================================================================================================================
    //FATURA
    //==================================================================================================================

    private Invoice findOrCreateInvoice(CreditCard card, LocalDate transactionDate) {

        YearMonth referencePeriod = resolveInvoicePeriod(
                transactionDate, card.getClosingDay()
        );

        return invoiceRepository.findByCardIdAndReferencePeriod(
                card.getId(),
                referencePeriod
        )
                .orElseGet(() -> {

                    LocalDate closingDate = LocalDate.of(
                            referencePeriod.getYear(), referencePeriod.getMonth(), card.getClosingDay()
                    );

                    LocalDate dueDate = LocalDate.of(
                            referencePeriod.plusMonths(1).getYear(), referencePeriod.plusMonths(1).getMonth(), card.getDueDay()
                    );

                    Invoice invoice = Invoice.builder()
                            .card(card)
                            .referencePeriod(referencePeriod)
                            .startDate(closingDate.minusMonths(1).plusDays(1))
                            .closingDate(closingDate)
                            .dueDate(dueDate)
                            .status(InvoiceStatus.OPEN)
                            .user(card.getUser())
                            .build();

                    return invoiceRepository.save(invoice);
        });
    }

    //==================================================================================================================

    private YearMonth resolveInvoicePeriod(LocalDate transactionDate, Integer closingDay) {
        if (transactionDate.getDayOfMonth() > closingDay) {
            return YearMonth.from(transactionDate.plusMonths(1));
        }
        return YearMonth.from(transactionDate);
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

    //==================================================================================================================
    //MODIFICA O SALDO
    //==================================================================================================================

    private void applyBalanceEffect(Transaction transaction) {
        if (transaction.getStatus() != TransactionStatus.CONFIRMED) {
            return;
        }

        if (transaction.getAccount() == null) {
            return;
        }

        Account account = transaction.getAccount();

        BigDecimal currentBalance = account.getCurrentBalance();

        if (transaction.getTransactionType() == TransactionType.INCOME){
            account.setCurrentBalance(currentBalance.add(transaction.getAmount()
                )
            );
        } else {
            account.setCurrentBalance(currentBalance.subtract(transaction.getAmount()
                )
            );
        }
        accountRepository.save(account);
    }
}
