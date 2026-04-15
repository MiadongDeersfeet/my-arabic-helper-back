package com.arabichelper.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// 로그인/회원가입 응답 DTO
@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String userId;
    private String role;
}
