package com.arabichelper.backend.common;

import java.time.Instant;

// API 공통 응답 래퍼
public record ApiResponse<T>(
        String message,
        T data,
        boolean success,
        Instant timestamp
) {
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(message, data, true, Instant.now());
    }

    public static <T> ApiResponse<T> fail(String message, T data) {
        return new ApiResponse<>(message, data, false, Instant.now());
    }
}
