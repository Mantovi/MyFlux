package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.CategoryResponseDTO;
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