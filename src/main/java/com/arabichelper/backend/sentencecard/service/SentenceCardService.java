package com.arabichelper.backend.sentencecard.service;

import com.arabichelper.backend.sentencecard.dto.SentenceCardRequestDTO;
import com.arabichelper.backend.sentencecard.dto.SentenceCardResponseDTO;

import java.util.List;

public interface SentenceCardService {
    List<SentenceCardResponseDTO> getCardsByCategory(String categoryId);

    SentenceCardResponseDTO getCard(String cardId);

    SentenceCardResponseDTO createCard(SentenceCardRequestDTO requestDto);

    SentenceCardResponseDTO updateCard(String cardId, SentenceCardRequestDTO requestDto);

    SentenceCardResponseDTO deactivateCard(String cardId);
}
