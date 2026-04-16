package com.arabichelper.backend.sentencecard.repository;

import com.arabichelper.backend.sentencecard.document.SentenceCardDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SentenceCardRepository extends MongoRepository<SentenceCardDocument, String> {
    List<SentenceCardDocument> findAllByCategoryIdAndActiveTrueOrderByCardNoAsc(String categoryId);

    Optional<SentenceCardDocument> findByIdAndActiveTrue(String id);
}
