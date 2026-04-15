package com.arabichelper.backend.study;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

// 학습 기록 컬렉션 문서
@Data
@Document(collection = "study_records")
public class StudyRecord {

    @Id
    private String id;

    // 어떤 타입의 학습인지 (WORD / SENTENCE)
    @NotBlank(message = "studyType 값은 필수입니다.")
    private String studyType;

    // 맞춘 개수
    @Min(value = 0, message = "correctCount는 0 이상이어야 합니다.")
    private int correctCount;

    // 총 문제 수
    @Min(value = 1, message = "totalCount는 1 이상이어야 합니다.")
    private int totalCount;

    // 점수(퍼센트)
    @Min(value = 0, message = "score는 0 이상이어야 합니다.")
    @Max(value = 100, message = "score는 100 이하여야 합니다.")
    private int score;

    // 메모
    private String memo;

    // 클라이언트에서 보내지 않아도 서버에서 자동 세팅
    private Instant createdAt;
}
