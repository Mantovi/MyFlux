package com.mantovi.MyFlux.service;


import com.mantovi.MyFlux.dto.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.CategoryResponseDTO;
import com.mantovi.MyFlux.model.User;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO request, User user);

    CategoryResponseDTO createCategoryGlobal(CategoryRequestDTO request);

    List<CategoryResponseDTO> listCategoriesByUser (UUID userId);
}