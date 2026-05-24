package com.mantovi.MyFlux.dto.filter;

import com.mantovi.MyFlux.model.*;
import org.hibernate.query.SortDirection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionFilterDTO (

  LocalDate date,

  TransactionType transactionType,
  PaymentMethodType paymentMethodType,
  TransactionStatus status,

  UUID categoryId,
  UUID accountId,
  UUID creditCardId,

  BigDecimal minAmount,
  BigDecimal maxAmount,
  String description,

  String sortBy,
  SortDirection direction
) {}
