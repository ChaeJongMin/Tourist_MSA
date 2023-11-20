package com.auth.authservice.Config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Base64;

//토큰 관련 설정 정보를 관리하는 클래스
@Configuration
public class TokenConfig {
    @Value("${token.secret}")
    private String secretKey;

    @Bean
    @Qualifier("myTokenKey")
    public Key tokenKey() {
        // 토큰에 사용될 키를 생성
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        // 데이터 무결성을 확인하기 위해 HMAC 알고리즘 사용
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
