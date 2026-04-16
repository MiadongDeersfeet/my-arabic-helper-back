package com.arabichelper.backend.sentencecard.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryAudioService {

    @Value("${app.audio.cloudinary.cloud-name:${CLOUDINARY_CLOUD_NAME:}}")
    private String cloudName;

    @Value("${app.audio.cloudinary.api-key:${CLOUDINARY_API_KEY:}}")
    private String apiKey;

    @Value("${app.audio.cloudinary.api-secret:${CLOUDINARY_API_SECRET:}}")
    private String apiSecret;

    @Value("${app.audio.cloudinary.folder:${CLOUDINARY_AUDIO_FOLDER:my-arabic-helper/audio}}")
    private String audioFolder;

    public String uploadAudio(MultipartFile file) throws IOException {
        if (!isConfigured()) {
            throw new IllegalStateException("Cloudinary 환경변수(CLOUDINARY_CLOUD_NAME/API_KEY/API_SECRET)가 누락되었습니다.");
        }

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));

        String originalName = file.getOriginalFilename() == null ? "audio.webm" : file.getOriginalFilename();
        String publicId = originalName.replaceAll("\\.[^.]+$", "") + "-" + UUID.randomUUID();

        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video",
                        "folder", audioFolder,
                        "public_id", publicId,
                        "overwrite", true
                )
        );

        Object secureUrl = result.get("secure_url");
        return secureUrl == null ? "" : secureUrl.toString();
    }

    public boolean isConfigured() {
        return notBlank(cloudName) && notBlank(apiKey) && notBlank(apiSecret);
    }

    private boolean notBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
