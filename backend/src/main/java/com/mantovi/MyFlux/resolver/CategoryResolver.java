package com.mantovi.MyFlux.resolver;

import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.TransactionType;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryResolver {

    private final CategoryRepository categoryRepository;


    public Category validateCategory(UUID categoryId, TransactionType type, User user) {
        return findAndValidateCategory(categoryId, type, user);
    }

    private Category findAndValidateCategory(UUID categoryId, TransactionType type, User user) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        if (category.getType() != type) {
            throw new RuntimeException("O tipo da categoria e da transação não coincidem");
        }

        if (!category.isGlobal() &&
                (category.getUser() == null ||
                        !category.getUser().getId().equals(user.getId())
                )) {
            throw new RuntimeException("Usuário sem acesso a essa categoria");
        }

        return category;
    }
}
