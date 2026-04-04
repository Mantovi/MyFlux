package com.mantovi.MyFlux.mapper;

import com.mantovi.MyFlux.dto.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.CategoryResponseDTO;
import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.User;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public static Category toCategory(CategoryRequestDTO request, User user) {
        return Category.builder()
                .name(request.name())
                .type(request.type())
                .user(user)
                .isDefault(false)
                .build();
    }

    public static CategoryResponseDTO toResponseCategory(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType(),
                category.isDefault()
        );
    }
}