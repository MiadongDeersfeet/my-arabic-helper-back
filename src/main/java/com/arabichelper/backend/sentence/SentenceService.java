package com.arabichelper.backend.sentence;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

// 문장 도메인 비즈니스 로직
@Service
public class SentenceService {

    private final SentenceRepository sentenceRepository;

    public SentenceService(SentenceRepository sentenceRepository) {
        this.sentenceRepository = sentenceRepository;
    }

    public List<Sentence> findAll() {
        return sentenceRepository.findAll();
    }

    public Sentence findById(String id) {
        return sentenceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 문장을 찾을 수 없습니다. id=" + id));
    }

    public Sentence create(Sentence sentence) {
        sentence.setCreatedAt(Instant.now());
        sentence.setUpdatedAt(Instant.now());
        if (sentence.getIsActive() == null) {
            sentence.setIsActive(true);
        }
        return sentenceRepository.save(sentence);
    }

    public Sentence update(String id, Sentence request) {
        Sentence existing = findById(id);

        existing.setCategory(request.getCategory());
        existing.setArabicText(request.getArabicText());
        existing.setKoreanText(request.getKoreanText());
        existing.setIsActive(request.getIsActive() == null ? true : request.getIsActive());
        existing.setUpdatedAt(Instant.now());

        return sentenceRepository.save(existing);
    }

    public void delete(String id) {
        sentenceRepository.deleteById(id);
    }

    public List<Sentence> randomSentences(int size) {
        List<Sentence> allSentences = sentenceRepository.findAllByIsActiveTrue();
        Collections.shuffle(allSentences);
        return allSentences.stream().limit(size).toList();
    }
}
