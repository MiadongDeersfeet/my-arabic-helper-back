package com.arabichelper.backend.sentencecard.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "sentence_cards")
public class SentenceCardDocument {
    @Id
    private String id;
    private Integer cardNo;
    private String categoryId;
    private String categoryName;
    private Kor kor;
    private Arb arb;
    private Flow flow;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    @Data
    public static class Kor {
        private String text;
        private String audioUrl;
    }

    @Data
    public static class Arb {
        private String text;
        private String audioUrl;
    }

    @Data
    public static class Flow {
        private Integer countdownSec;
    }
}
