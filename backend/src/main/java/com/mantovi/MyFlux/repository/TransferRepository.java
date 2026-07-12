package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
