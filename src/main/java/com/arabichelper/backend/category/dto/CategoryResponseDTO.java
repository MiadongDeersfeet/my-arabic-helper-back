package com.arabichelper.backend.category.dto;

import java.time.Instant;

public record CategoryResponseDTO(
        String id,
        String name,
        String description,
        Boolean active,
        Integer sortOrder,
        Instant createdAt,
        Instant updatedAt
) {
}
