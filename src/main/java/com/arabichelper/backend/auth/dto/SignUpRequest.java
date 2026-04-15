package com.arabichelper.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// 회원가입 요청 DTO
@Data
public class SignUpRequest {

    @NotBlank(message = "user_id 값은 필수입니다.")
    private String userId;

    @NotBlank(message = "password 값은 필수입니다.")
    private String password;

    @NotBlank(message = "role 값은 필수입니다.")
    @Pattern(regexp = "admin|user", message = "role은 admin 또는 user만 가능합니다.")
    private String role;
}
