package com.arabichelper.backend.study;

import com.arabichelper.backend.sentence.Sentence;
import com.arabichelper.backend.word.Word;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 학습 API (랜덤 문제 + 기록 관리)
@RestController
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    // 랜덤 단어 학습 세트 조회
    @GetMapping("/words/random")
    public List<Word> randomWords(@RequestParam(defaultValue = "10") int size) {
        return studyService.randomWords(size);
    }

    // 랜덤 문장 학습 세트 조회
    @GetMapping("/sentences/random")
    public List<Sentence> randomSentences(@RequestParam(defaultValue = "10") int size) {
        return studyService.randomSentences(size);
    }

    // 학습 기록 저장
    @PostMapping("/records")
    public StudyRecord createRecord(@Valid @RequestBody StudyRecord record) {
        return studyService.createRecord(record);
    }

    // 학습 기록 전체 조회(최신순)
    @GetMapping("/records")
    public List<StudyRecord> getRecords() {
        return studyService.getRecords();
    }
}
