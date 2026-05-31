package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.transfer.TransferRequestDTO;
import com.mantovi.MyFlux.dto.transfer.TransferResponseDTO;
import com.mantovi.MyFlux.mapper.TransferMapper;
import com.mantovi.MyFlux.model.*;
import com.mantovi.MyFlux.repository.AccountRepository;
import com.mantovi.MyFlux.repository.CategoryRepository;
import com.mantovi.MyFlux.repository.TransactionRepository;
import com.mantovi.MyFlux.repository.TransferRepository;
import com.mantovi.MyFlux.service.TransferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final TransferMapper transferMapper;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public TransferResponseDTO createTransfer(TransferRequestDTO request, User user) {

        Account sourceAccount = findAndValidadeAccount(
                request.sourceAccountId(), user);

        Account destinationAccount = findAndValidadeAccount(
                request.destinationAccountId(), user);

        validadeTransfer(sourceAccount, destinationAccount, request.transferAmount());

        Transfer transfer = transferMapper.toTransfer(
                request, user, sourceAccount, destinationAccount);

        Transaction expenseTransaction = createExpenseTransaction(
                transfer, sourceAccount, request.transferAmount(), request.transferDate());

        Transaction incomeTransaction = createIncomeTransaction(
                transfer, destinationAccount, request.transferAmount(), request.transferDate());

        transfer.setExpenseTransaction(expenseTransaction);
        transfer.setIncomeTransaction(incomeTransaction);

        transferRepository.save(transfer);

        return transferMapper.toTransferResponse(transfer);
    }

    private void validadeTransfer(Account source, Account destination, BigDecimal amount) {
        if(source.getId().equals(destination.getId())) {
            throw new RuntimeException("Não é possível tranEferir para a mesma conta de origem");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("O valor deve ser maior que zero");
        }
    }

    private Account findAndValidadeAccount(UUID accountId, User user) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        return account;
    }

    private Transaction createExpenseTransaction(Transfer transfer, Account account, BigDecimal amount, LocalDate transferDate) {

        Category category = getDefaultTransferCategory();

        account.setCurrentBalance(
                account.getCurrentBalance()
                        .subtract(amount));

        accountRepository.save(account);

        return Transaction.builder()
                .user(transfer.getUser())
                .account(account)
                .amount(amount)
                .date(transferDate)
                .transactionType(TransactionType.TRANSFER)
                .paymentMethodType(PaymentMethodType.TRANSFER)
                .status(TransactionStatus.CONFIRMED)
                .description("Transferencia para: " + transfer.getDestinationAccount().getName())
                .observation(transfer.getObservation())
                .transfer(transfer)
                .category(category)
                .build();
    }

    private Transaction createIncomeTransaction(Transfer transfer, Account account, BigDecimal amount, LocalDate transferDate) {

        Category category = getDefaultTransferCategory();

        account.setCurrentBalance(
                account.getCurrentBalance()
                .add(amount)
        );

        accountRepository.save(account);

        return Transaction.builder()
                .user(transfer.getUser())
                .account(account)
                .amount(amount)
                .date(transferDate)
                .transactionType(TransactionType.TRANSFER)
                .paymentMethodType(PaymentMethodType.TRANSFER)
                .status(TransactionStatus.CONFIRMED)
                .description("Transferencia vinda de: " + transfer.getSourceAccount().getName())
                .observation(transfer.getObservation())
                .transfer(transfer)
                .category(category)
                .build();
    }

    private Category getDefaultTransferCategory() {
        return categoryRepository.findByNameAndTypeAndIsGlobal("Transferência", TransactionType.TRANSFER, true)
                .orElseThrow(() -> new RuntimeException("Categoria padrão de transferência não encontrada"));
    }
}
