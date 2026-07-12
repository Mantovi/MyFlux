package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, UUID> {
}
