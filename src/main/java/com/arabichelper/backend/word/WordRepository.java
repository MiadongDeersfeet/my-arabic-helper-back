package com.arabichelper.backend.word;

import org.springframework.data.mongodb.repository.MongoRepository;

// 단어 CRUD를 담당하는 Mongo Repository
public interface WordRepository extends MongoRepository<Word, String> {
}
