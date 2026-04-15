package com.arabichelper.backend.word;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 단어 CRUD API
@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    // 전체 단어 목록 조회
    @GetMapping
    public List<Word> findAll() {
        return wordService.findAll();
    }

    // 단어 단건 조회
    @GetMapping("/{id}")
    public Word findById(@PathVariable String id) {
        return wordService.findById(id);
    }

    // 단어 생성
    @PostMapping
    public Word create(@Valid @RequestBody Word word) {
        return wordService.create(word);
    }

    // 단어 수정
    @PutMapping("/{id}")
    public Word update(@PathVariable String id, @Valid @RequestBody Word word) {
        return wordService.update(id, word);
    }

    // 단어 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        wordService.delete(id);
    }
}
