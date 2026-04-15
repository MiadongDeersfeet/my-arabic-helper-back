package com.arabichelper.backend.sentence;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 문장 CRUD API
@RestController
@RequestMapping("/api/sentences")
public class SentenceController {

    private final SentenceService sentenceService;

    public SentenceController(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    // 전체 문장 목록 조회
    @GetMapping
    public List<Sentence> findAll() {
        return sentenceService.findAll();
    }

    // 문장 단건 조회
    @GetMapping("/{id}")
    public Sentence findById(@PathVariable String id) {
        return sentenceService.findById(id);
    }

    // 문장 생성
    @PostMapping
    public Sentence create(@Valid @RequestBody Sentence sentence) {
        return sentenceService.create(sentence);
    }

    // 문장 수정
    @PutMapping("/{id}")
    public Sentence update(@PathVariable String id, @Valid @RequestBody Sentence sentence) {
        return sentenceService.update(id, sentence);
    }

    // 문장 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        sentenceService.delete(id);
    }
}
