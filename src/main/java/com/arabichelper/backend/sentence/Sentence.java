package com.arabichelper.backend.sentence;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

// 문장 컬렉션 문서
@Data
@Document(collection = "sentences")
public class Sentence {

    @Id
    private String id;

    // 아랍어 문장
    @NotBlank(message = "arabic 값은 필수입니다.")
    private String arabic;

    // 해석(한국어)
    @NotBlank(message = "translation 값은 필수입니다.")
    private String translation;

    // 발음
    private String pronunciation;

    // 문맥/태그 (예: 인사, 식당, 여행)
    private String topic;

    private Instant createdAt;
    private Instant updatedAt;
}
