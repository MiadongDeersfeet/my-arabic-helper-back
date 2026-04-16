package com.arabichelper.backend.sentence;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

// 문장 CRUD를 담당하는 Mongo Repository
public interface SentenceRepository extends MongoRepository<Sentence, String> {
    List<Sentence> findAllByIsActiveTrue();
}
