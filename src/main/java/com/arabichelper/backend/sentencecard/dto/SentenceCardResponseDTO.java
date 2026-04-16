package com.arabichelper.backend.sentencecard.dto;

import java.time.Instant;

public record SentenceCardResponseDTO(
        String id,
        Integer cardNo,
        String categoryId,
        String categoryName,
        KorDTO kor,
        ArbDTO arb,
        FlowDTO flow,
        Boolean active,
        Instant createdAt,
        Instant updatedAt
) {
    public record KorDTO(
            String text,
            String audioUrl
    ) {
    }

    public record ArbDTO(
            String text,
            String audioUrl
    ) {
    }

    public record FlowDTO(
            Integer countdownSec
    ) {
    }
}
