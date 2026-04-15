package com.arabichelper.backend.word;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

// 단어 도메인 비즈니스 로직
@Service
public class WordService {

    private final WordRepository wordRepository;

    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    public Word findById(String id) {
        return wordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 단어를 찾을 수 없습니다. id=" + id));
    }

    public Word create(Word word) {
        // 신규 생성 시 서버에서 타임스탬프 부여
        word.setCreatedAt(Instant.now());
        word.setUpdatedAt(Instant.now());
        return wordRepository.save(word);
    }

    public Word update(String id, Word request) {
        Word existing = findById(id);

        existing.setArabic(request.getArabic());
        existing.setPronunciation(request.getPronunciation());
        existing.setMeaning(request.getMeaning());
        existing.setExample(request.getExample());
        existing.setLevel(request.getLevel());
        existing.setUpdatedAt(Instant.now());

        return wordRepository.save(existing);
    }

    public void delete(String id) {
        wordRepository.deleteById(id);
    }

    public List<Word> randomWords(int size) {
        List<Word> allWords = wordRepository.findAll();
        // 학습용 랜덤 출제를 위해 목록을 셔플
        Collections.shuffle(allWords);
        return allWords.stream().limit(size).toList();
    }
}
