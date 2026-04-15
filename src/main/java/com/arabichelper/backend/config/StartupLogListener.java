package com.arabichelper.backend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// 내장 서버 초기화 이후 실제 포트로 연결 성공 메시지 출력
@Component
public class StartupLogListener {

    @EventListener
    public void onWebServerReady(WebServerInitializedEvent event) {
        int port = event.getWebServer().getPort();
        System.out.println(port + " 연결성공");
    }
}
