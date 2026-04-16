package com.arabichelper.backend.sentencecard.service;

import com.arabichelper.backend.category.document.CategoryDocument;
import com.arabichelper.backend.category.repository.CategoryRepository;
import com.arabichelper.backend.sentencecard.document.SentenceCardDocument;
import com.arabichelper.backend.sentencecard.dto.SentenceCardRequestDTO;
import com.arabichelper.backend.sentencecard.dto.SentenceCardResponseDTO;
import com.arabichelper.backend.sentencecard.repository.SentenceCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SentenceCardServiceImpl implements SentenceCardService {
    private final SentenceCardRepository sentenceCardRepository;
    private final CategoryRepository categoryRepository;

    public SentenceCardServiceImpl(SentenceCardRepository sentenceCardRepository, CategoryRepository categoryRepository) {
        this.sentenceCardRepository = sentenceCardRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SentenceCardResponseDTO> getCardsByCategory(String categoryId) {
        return sentenceCardRepository.findAllByCategoryIdAndActiveTrueOrderByCardNoAsc(categoryId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public SentenceCardResponseDTO getCard(String cardId) {
        return toResponseDto(findActiveCardById(cardId));
    }

    @Override
    public SentenceCardResponseDTO createCard(SentenceCardRequestDTO requestDto) {
        SentenceCardDocument document = new SentenceCardDocument();
        applyRequest(document, requestDto);
        document.setActive(requestDto.active() == null ? true : requestDto.active());
        document.setCreatedAt(Instant.now());
        document.setUpdatedAt(Instant.now());
        return toResponseDto(sentenceCardRepository.save(document));
    }

    @Override
    public SentenceCardResponseDTO updateCard(String cardId, SentenceCardRequestDTO requestDto) {
        SentenceCardDocument document = findActiveCardById(cardId);
        applyRequest(document, requestDto);
        document.setActive(requestDto.active() == null ? true : requestDto.active());
        document.setUpdatedAt(Instant.now());
        return toResponseDto(sentenceCardRepository.save(document));
    }

    @Override
    public SentenceCardResponseDTO deactivateCard(String cardId) {
        SentenceCardDocument document = findActiveCardById(cardId);
        document.setActive(false);
        document.setUpdatedAt(Instant.now());
        return toResponseDto(sentenceCardRepository.save(document));
    }

    private SentenceCardDocument findActiveCardById(String cardId) {
        return sentenceCardRepository.findByIdAndActiveTrue(cardId)
                .orElseThrow(() -> new NoSuchElementException("문장카드를 찾을 수 없습니다. id=" + cardId));
    }

    private void applyRequest(SentenceCardDocument document, SentenceCardRequestDTO requestDto) {
        CategoryDocument category = resolveCategory(requestDto.categoryId(), requestDto.categoryName());

        document.setCardNo(requestDto.cardNo());
        document.setCategoryId(category.getId());
        document.setCategoryName(category.getName());

        SentenceCardDocument.Kor kor = new SentenceCardDocument.Kor();
        kor.setText(requestDto.kor().text());
        kor.setAudioUrl(requestDto.kor().audioUrl());
        document.setKor(kor);

        SentenceCardDocument.Arb arb = new SentenceCardDocument.Arb();
        arb.setText(requestDto.arb().text());
        arb.setAudioUrl(requestDto.arb().audioUrl());
        document.setArb(arb);

        SentenceCardDocument.Flow flow = new SentenceCardDocument.Flow();
        flow.setCountdownSec(requestDto.flow().countdownSec());
        document.setFlow(flow);
    }

    private CategoryDocument resolveCategory(String categoryId, String categoryNameRaw) {
        String categoryName = categoryNameRaw == null ? "" : categoryNameRaw.trim();
        if (categoryName.isBlank()) {
            throw new ResponseStatusException(BAD_REQUEST, "categoryName 값은 필수입니다.");
        }

        if (categoryId != null && !categoryId.isBlank()) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NoSuchElementException("카테고리를 찾을 수 없습니다. id=" + categoryId));
        }

        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseGet(() -> {
                    int nextSortOrder = categoryRepository.findTopByOrderBySortOrderDesc()
                            .map(existing -> (existing.getSortOrder() == null ? 0 : existing.getSortOrder()) + 1)
                            .orElse(1);

                    CategoryDocument category = new CategoryDocument();
                    category.setName(categoryName);
                    category.setDescription("");
                    category.setActive(true);
                    category.setSortOrder(nextSortOrder);
                    category.setCreatedAt(Instant.now());
                    category.setUpdatedAt(Instant.now());
                    return categoryRepository.save(category);
                });
    }

    private SentenceCardResponseDTO toResponseDto(SentenceCardDocument document) {
        return new SentenceCardResponseDTO(
                document.getId(),
                document.getCardNo(),
                document.getCategoryId(),
                document.getCategoryName(),
                new SentenceCardResponseDTO.KorDTO(
                        document.getKor() != null ? document.getKor().getText() : null,
                        document.getKor() != null ? document.getKor().getAudioUrl() : null
                ),
                new SentenceCardResponseDTO.ArbDTO(
                        document.getArb() != null ? document.getArb().getText() : null,
                        document.getArb() != null ? document.getArb().getAudioUrl() : null
                ),
                new SentenceCardResponseDTO.FlowDTO(
                        document.getFlow() != null ? document.getFlow().getCountdownSec() : null
                ),
                document.getActive(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
