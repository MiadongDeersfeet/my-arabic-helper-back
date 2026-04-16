package com.arabichelper.backend.category.repository;

import com.arabichelper.backend.category.document.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {
    List<CategoryDocument> findAllByActiveTrueOrderBySortOrderAsc();

    Optional<CategoryDocument> findByNameIgnoreCase(String name);

    Optional<CategoryDocument> findTopByOrderBySortOrderDesc();
}
