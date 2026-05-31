package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByUser_IdAndNameAndType( UUID userId, String name, TransactionType type);
    boolean existsByNameAndIsGlobalTrue(String name);

    @Query("SELECT c FROM Category c WHERE c.isGlobal = true OR c.user.id = :userId")
    List<Category> findByIsGlobalTrueOrUserId(UUID userId);

    Optional<Category> findByNameAndTypeAndIsGlobal(String name, TransactionType type, boolean isGlobal);
}
