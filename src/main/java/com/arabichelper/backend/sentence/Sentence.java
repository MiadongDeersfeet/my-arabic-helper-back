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

    // 카테고리(예: 10과)
    @NotBlank(message = "category 값은 필수입니다.")
    private String category;

    // 아랍어 문장
    @NotBlank(message = "arabicText 값은 필수입니다.")
    private String arabicText;

    // 해석(한국어)
    @NotBlank(message = "koreanText 값은 필수입니다.")
    private String koreanText;

    // 활성화 여부(학습 출제 대상 여부)
    private Boolean isActive;

    private Instant createdAt;
    private Instant updatedAt;
}
