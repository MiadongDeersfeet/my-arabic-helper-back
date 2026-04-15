package com.arabichelper.backend.study;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// 학습 기록 조회/저장을 담당하는 Mongo Repository
public interface StudyRecordRepository extends MongoRepository<StudyRecord, String> {

    // 최신 기록이 먼저 오도록 생성일 역순 정렬
    List<StudyRecord> findAllByOrderByCreatedAtDesc();
}
