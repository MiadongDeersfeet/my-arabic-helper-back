package com.arabichelper.backend.auth;

import com.arabichelper.backend.auth.dto.AuthResponse;
import com.arabichelper.backend.auth.dto.LoginRequest;
import com.arabichelper.backend.auth.dto.SignUpRequest;
import com.arabichelper.backend.schema.UserRepository;
import com.arabichelper.backend.schema.UserSchema;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// 회원가입/로그인 비즈니스 로직
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public AuthResponse signUp(SignUpRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 user_id 입니다.");
        }

        UserSchema user = new UserSchema();
        user.setUserNo(nextUserNo());
        user.setUserId(request.getUserId());
        // 비밀번호는 반드시 암호화해서 저장
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        UserSchema savedUser = userRepository.save(user);
        String token = jwtProvider.createToken(savedUser.getUserId(), savedUser.getRole());

        return new AuthResponse(token, savedUser.getUserId(), savedUser.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        UserSchema user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."));

        boolean matched = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!matched) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.createToken(user.getUserId(), user.getRole());
        return new AuthResponse(token, user.getUserId(), user.getRole());
    }

    private long nextUserNo() {
        return userRepository.findTopByOrderByUserNoDesc()
                .map(lastUser -> lastUser.getUserNo() + 1)
                .orElse(1L);
    }
}
