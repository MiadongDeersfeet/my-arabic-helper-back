package com.arabichelper.backend.category.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "categories")
public class CategoryDocument {
    @Id
    private String id;
    private String name;
    private String description;
    private Boolean active;
    private Integer sortOrder;
    private Instant createdAt;
    private Instant updatedAt;
}
