package com.arabichelper.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 로그인 요청 DTO
@Data
public class LoginRequest {

    @NotBlank(message = "user_id 값은 필수입니다.")
    private String userId;

    @NotBlank(message = "password 값은 필수입니다.")
    private String password;
}
