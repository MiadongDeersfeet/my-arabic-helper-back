package com.arabichelper.backend.study;

import com.arabichelper.backend.sentence.Sentence;
import com.arabichelper.backend.sentence.SentenceService;
import com.arabichelper.backend.word.Word;
import com.arabichelper.backend.word.WordService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

// 학습 관련 기능(랜덤 문제/기록 저장) 비즈니스 로직
@Service
public class StudyService {

    private final WordService wordService;
    private final SentenceService sentenceService;
    private final StudyRecordRepository studyRecordRepository;

    public StudyService(WordService wordService,
                        SentenceService sentenceService,
                        StudyRecordRepository studyRecordRepository) {
        this.wordService = wordService;
        this.sentenceService = sentenceService;
        this.studyRecordRepository = studyRecordRepository;
    }

    public List<Word> randomWords(int size) {
        return wordService.randomWords(size);
    }

    public List<Sentence> randomSentences(int size) {
        return sentenceService.randomSentences(size);
    }

    public StudyRecord createRecord(StudyRecord record) {
        // 기록 생성 시점은 서버 표준 시간으로 저장
        record.setCreatedAt(Instant.now());
        return studyRecordRepository.save(record);
    }

    public List<StudyRecord> getRecords() {
        return studyRecordRepository.findAllByOrderByCreatedAtDesc();
    }
}
