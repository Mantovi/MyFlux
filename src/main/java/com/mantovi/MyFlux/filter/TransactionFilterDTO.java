package com.mantovi.MyFlux.filter;

import com.mantovi.MyFlux.model.*;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionFilterDTO (

  LocalDate date,
  LocalDate startDate,
  LocalDate endDate,

  TransactionType transactionType,
  PaymentMethodType paymentMethodType,
  TransactionStatus status,

  UUID categoryId,
  UUID accountId,
  UUID cardId,

  BigDecimal minAmount,
  BigDecimal maxAmount,
  String description,

  TransactionSortField sortBy,
  Sort.Direction direction
) {}
