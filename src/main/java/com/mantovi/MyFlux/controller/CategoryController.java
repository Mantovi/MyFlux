package com.mantovi.MyFlux.controller;

import com.mantovi.MyFlux.dto.CategoryRequestDTO;
import com.mantovi.MyFlux.dto.CategoryResponseDTO;
import com.mantovi.MyFlux.model.User;
import com.mantovi.MyFlux.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Valid CategoryRequestDTO request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(categoryService.createCategory(request, user));
    }

    @PostMapping("/global")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDTO> createGlobal(@RequestBody @Valid CategoryRequestDTO request) {
        return ResponseEntity.ok(categoryService.createCategoryGlobal(request));
    }
}