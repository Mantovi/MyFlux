package com.mantovi.MyFlux.repository;

import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByNameAndUser(String name, User user);
}