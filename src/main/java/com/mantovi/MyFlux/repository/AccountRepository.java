package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
