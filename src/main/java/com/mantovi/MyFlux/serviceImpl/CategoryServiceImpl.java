package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.CategoryResponseDTO;
import com.mantovi.MyFlux.mapper.CategoryMapper;
import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.CategoryRepository;
import com.mantovi.MyFlux.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request, User user) {
        if (categoryRepository.existsByNameAndUser(request.name(), user)) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = categoryMapper.toCategory(request, user);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseCategory(savedCategory);
    }

    @Override
    public CategoryResponseDTO createCategoryGlobal(CategoryRequestDTO request) {
        if (categoryRepository.existsByNameAndIsGlobalTrue(request.name())) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = categoryMapper.toCategoryGlobal(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseCategory(savedCategory);
    }

    @Override
    public List<CategoryResponseDTO> listCategoriesByUser(UUID userId) {
        List<Category> categories = categoryRepository.findByIsGlobalTrueOrUserId(userId);
        return categories.stream()
                .map(categoryMapper::toResponseCategoryToUser)
                .toList();
    }
}