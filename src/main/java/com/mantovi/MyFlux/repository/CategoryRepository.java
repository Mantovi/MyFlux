package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByNameAndUser(String name, User user);
    boolean existsByNameAndIsGlobalTrue(String name);

    @Query("SELECT c FROM Category c WHERE c.isGlobal = true OR c.user.id = :userId")
    List<Category> findByIsGlobalTrueOrUserId(UUID userId);
}
