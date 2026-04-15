package com.arabichelper.backend.auth;

import com.arabichelper.backend.auth.dto.AuthResponse;
import com.arabichelper.backend.auth.dto.LoginRequest;
import com.arabichelper.backend.auth.dto.SignUpRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// 회원가입/로그인 API
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입 후 바로 토큰 발행
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }

    // 로그인 성공 시 토큰 발행
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
