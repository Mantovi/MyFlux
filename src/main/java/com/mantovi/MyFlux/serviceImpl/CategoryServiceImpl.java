package com.mantovi.MyFlux.serviceImpl;

import com.mantovi.MyFlux.dto.category.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.category.CategoryResponseDTO;
import com.mantovi.MyFlux.dto.category.CategoryUpdateRequestDTO;
import com.mantovi.MyFlux.mapper.CategoryMapper;
import com.mantovi.MyFlux.model.Category;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.repository.CategoryRepository;
import com.mantovi.MyFlux.repository.TransactionRepository;
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
    private final TransactionRepository transactionRepository;


    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request, User user) {
        boolean categoryAlreadyExists =
                categoryRepository.existsByUser_IdAndNameAndType(user.getId(), request.name(), request.type());

        if (categoryAlreadyExists) {
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
    public CategoryResponseDTO update(UUID categoryId, CategoryUpdateRequestDTO request, User user) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        categoryMapper.updateCategory(category, request, user);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponseCategory(savedCategory);
    }

    @Override
    public void deleteById(UUID categoryId, User user) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        if (!category.isGlobal() &&
                (category.getUser() == null ||
                        !category.getUser().getId().equals(user.getId())
                )) {
            throw new RuntimeException("Acesso Negado");
        }
        if (category.isGlobal()) {
            throw new RuntimeException("Categorias globais não podem ser removidas");
        }
        if (transactionRepository.existsByCategoryId(categoryId)) {
            throw new RuntimeException("Não é possível excluir categorias que estão vinculadas a alguma transação");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryResponseDTO> listCategoriesByUser(UUID userId) {
        List<Category> categories = categoryRepository.findByIsGlobalTrueOrUserId(userId);
        return categories.stream()
                .map(categoryMapper::toResponseCategoryToUser)
                .toList();
    }
}