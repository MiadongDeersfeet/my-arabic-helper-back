package com.arabichelper.backend.category.service;

import com.arabichelper.backend.category.document.CategoryDocument;
import com.arabichelper.backend.category.dto.CategoryResponseDTO;
import com.arabichelper.backend.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDTO> getActiveCategories() {
        return categoryRepository.findAllByActiveTrueOrderBySortOrderAsc().stream()
                .map(this::toResponseDto)
                .toList();
    }

    private CategoryResponseDTO toResponseDto(CategoryDocument category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getActive(),
                category.getSortOrder(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
