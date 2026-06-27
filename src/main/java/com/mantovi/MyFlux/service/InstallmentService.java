package com.mantovi.MyFlux.service;

import com.mantovi.MyFlux.dto.transaction.TransactionRequestDTO;
import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.Transaction;
import com.mantovi.MyFlux.model.User;

public interface InstallmentService {
    Transaction createInstallmentTransactions(TransactionRequestDTO request, User user, Category category);
}
