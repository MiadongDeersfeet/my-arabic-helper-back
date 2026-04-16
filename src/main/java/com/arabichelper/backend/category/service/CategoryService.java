package com.arabichelper.backend.category.service;

import com.arabichelper.backend.category.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getActiveCategories();
}
