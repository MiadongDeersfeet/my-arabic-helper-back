package com.arabichelper.backend.category.controller;

import com.arabichelper.backend.category.dto.CategoryResponseDTO;
import com.arabichelper.backend.category.service.CategoryService;
import com.arabichelper.backend.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<List<CategoryResponseDTO>> getCategories() {
        return ApiResponse.ok("카테고리 목록 조회 성공", categoryService.getActiveCategories());
    }
}
