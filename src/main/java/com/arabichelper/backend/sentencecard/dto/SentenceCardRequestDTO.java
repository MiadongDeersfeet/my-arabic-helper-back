package com.arabichelper.backend.sentencecard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SentenceCardRequestDTO(
        @NotNull @Min(1) Integer cardNo,
        String categoryId,
        @NotBlank String categoryName,
        @NotNull @Valid KorDTO kor,
        @NotNull @Valid ArbDTO arb,
        @NotNull @Valid FlowDTO flow,
        Boolean active
) {
    public record KorDTO(
            @NotBlank String text,
            @NotBlank String audioUrl
    ) {
    }

    public record ArbDTO(
            @NotBlank String text,
            @NotBlank String audioUrl
    ) {
    }

    public record FlowDTO(
            @NotNull @Min(1) Integer countdownSec
    ) {
    }
}
