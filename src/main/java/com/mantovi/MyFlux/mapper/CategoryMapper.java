package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.category.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.category.CategoryResponseDTO;
import com.mantovi.MyFlux.dto.category.CategoryUpdateRequestDTO;
import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toCategory(CategoryRequestDTO request, User user) {
        return Category.builder()
                .name(request.name())
                .type(request.type())
                .user(user)
                .isGlobal(false)
                .build();
    }

    public Category toCategoryGlobal(CategoryRequestDTO request) {
        return Category.builder()
                .name(request.name())
                .type(request.type())
                .isGlobal(true)
                .build();
    }

    public void updateCategory(Category category, CategoryUpdateRequestDTO request, User user) {
        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.type() != null) {
            category.setType(request.type());
        }
        if (user != null) {
            category.setUser(user);
        }
    }

    public CategoryResponseDTO toResponseCategory(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType(),
                category.isGlobal()
        );
    }

    public CategoryResponseDTO toResponseCategoryToUser(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType(),
                category.isGlobal()
        );
    }
}