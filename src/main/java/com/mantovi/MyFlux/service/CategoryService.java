package com.mantovi.MyFlux.service;


import com.mantovi.MyFlux.dto.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.CategoryResponseDTO;
import com.mantovi.MyFlux.model.User;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO request, User user);

    CategoryResponseDTO createCategoryGlobal(CategoryRequestDTO request);
}