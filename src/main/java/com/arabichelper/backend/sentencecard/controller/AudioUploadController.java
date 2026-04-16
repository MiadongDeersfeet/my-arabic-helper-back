package com.arabichelper.backend.sentencecard.controller;

import com.arabichelper.backend.common.ApiResponse;
import com.arabichelper.backend.sentencecard.service.CloudinaryAudioService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/audio")
public class AudioUploadController {
    private static final long MAX_AUDIO_SIZE_BYTES = 15L * 1024 * 1024;

    private final CloudinaryAudioService cloudinaryAudioService;

    public AudioUploadController(CloudinaryAudioService cloudinaryAudioService) {
        this.cloudinaryAudioService = cloudinaryAudioService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Map<String, String>> uploadAudio(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.fail("업로드할 파일이 비어 있습니다.", null);
        }

        if (file.getSize() > MAX_AUDIO_SIZE_BYTES) {
            return ApiResponse.fail("오디오 파일은 최대 15MB까지 업로드할 수 있습니다.", null);
        }

        if (!cloudinaryAudioService.isConfigured()) {
            return ApiResponse.fail(
                    "Cloudinary 설정이 누락되었습니다. CLOUDINARY_CLOUD_NAME/API_KEY/API_SECRET를 설정하세요.",
                    null
            );
        }

        try {
            String secureUrl = cloudinaryAudioService.uploadAudio(file);
            if (secureUrl.isBlank()) {
                return ApiResponse.fail("Cloudinary 업로드 응답에 secure_url이 없습니다.", null);
            }

            return ApiResponse.ok("오디오 업로드 성공", Map.of("audioUrl", secureUrl));
        } catch (Exception ex) {
            return ApiResponse.fail("오디오 업로드 실패: " + ex.getMessage(), null);
        }
    }

}
