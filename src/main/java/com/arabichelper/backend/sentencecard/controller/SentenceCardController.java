package com.arabichelper.backend.sentencecard.controller;

import com.arabichelper.backend.common.ApiResponse;
import com.arabichelper.backend.sentencecard.dto.SentenceCardRequestDTO;
import com.arabichelper.backend.sentencecard.dto.SentenceCardResponseDTO;
import com.arabichelper.backend.sentencecard.service.SentenceCardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SentenceCardController {
    private final SentenceCardService sentenceCardService;

    public SentenceCardController(SentenceCardService sentenceCardService) {
        this.sentenceCardService = sentenceCardService;
    }

    @GetMapping("/api/categories/{categoryId}/sentence-cards")
    public ApiResponse<List<SentenceCardResponseDTO>> getCardsByCategory(@PathVariable String categoryId) {
        return ApiResponse.ok(
                "카테고리 문장카드 목록 조회 성공",
                sentenceCardService.getCardsByCategory(categoryId)
        );
    }

    @GetMapping("/api/sentence-cards/{cardId}")
    public ApiResponse<SentenceCardResponseDTO> getCard(@PathVariable String cardId) {
        return ApiResponse.ok("문장카드 조회 성공", sentenceCardService.getCard(cardId));
    }

    @PostMapping("/api/sentence-cards")
    public ApiResponse<SentenceCardResponseDTO> createCard(@Valid @RequestBody SentenceCardRequestDTO requestDto) {
        return ApiResponse.ok("문장카드 등록 성공", sentenceCardService.createCard(requestDto));
    }

    @PutMapping("/api/sentence-cards/{cardId}")
    public ApiResponse<SentenceCardResponseDTO> updateCard(
            @PathVariable String cardId,
            @Valid @RequestBody SentenceCardRequestDTO requestDto
    ) {
        return ApiResponse.ok("문장카드 수정 성공", sentenceCardService.updateCard(cardId, requestDto));
    }

    @PatchMapping("/api/sentence-cards/{cardId}/deactivate")
    public ApiResponse<SentenceCardResponseDTO> deactivateCard(@PathVariable String cardId) {
        return ApiResponse.ok("문장카드 비활성화 성공", sentenceCardService.deactivateCard(cardId));
    }
}
