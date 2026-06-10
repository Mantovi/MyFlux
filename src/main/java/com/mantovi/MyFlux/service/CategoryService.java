package com.mantovi.MyFlux.service;


import com.mantovi.MyFlux.dto.category.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.category.CategoryResponseDTO;
import com.mantovi.MyFlux.dto.category.CategoryUpdateRequestDTO;
import com.mantovi.MyFlux.model.User;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO request, User user);

    CategoryResponseDTO createCategoryGlobal(CategoryRequestDTO request);

    CategoryResponseDTO update(UUID categoryId, CategoryUpdateRequestDTO request, User user);

    void deleteById(UUID categoryId, User user);

    List<CategoryResponseDTO> listCategoriesByUser (UUID userId);
}