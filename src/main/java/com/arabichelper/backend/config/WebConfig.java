package com.arabichelper.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 프론트엔드(로컬/Vercel)에서 백엔드 API 접근 가능하도록 CORS 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // application.yml가 없어도 Render 환경변수(APP_CORS_ALLOWED_ORIGINS/ALLOWED_ORIGINS)로 동작하도록 기본값 제공
    @Value("${app.cors.allowed-origins:${APP_CORS_ALLOWED_ORIGINS:${ALLOWED_ORIGINS:http://localhost:5173}}}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
