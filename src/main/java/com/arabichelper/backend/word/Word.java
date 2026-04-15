package com.arabichelper.backend.word;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

// 단어 컬렉션 문서
@Data
@Document(collection = "words")
public class Word {

    @Id
    private String id;

    // 아랍어 원문
    @NotBlank(message = "arabic 값은 필수입니다.")
    private String arabic;

    // 발음(한글/로마자 등)
    private String pronunciation;

    // 뜻(한국어)
    @NotBlank(message = "meaning 값은 필수입니다.")
    private String meaning;

    // 예문(선택)
    private String example;

    // 난이도(초급/중급/고급 등 자유 텍스트)
    private String level;

    // 생성/수정 시각
    private Instant createdAt;
    private Instant updatedAt;
}
