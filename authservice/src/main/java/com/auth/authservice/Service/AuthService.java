package com.auth.authservice.Service;

import com.auth.authservice.Domain.RefreshTokenEntity;
import com.auth.authservice.Dto.ResponseTokenDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

//AuthService 인터페이스
public interface AuthService {

    //access 발급
    String generateAccess(String userId, HttpServletResponse response);

    //refresh 발급
    String generateRefresh();

    // refreshTokenEntity 객체를 Table에 저장 또는 수정
    @Transactional
    String saveRefreshToken(String userId, HttpServletResponse response);

    // 모든 토큰 생성 (generateAccess, generateRefresh 메소드 호출)
    ResponseTokenDto generateAllTokens(String userId, HttpServletResponse response);

    //Token 값으로 유저 네임 찾기
    String findUserIdByToken(String value);
}
