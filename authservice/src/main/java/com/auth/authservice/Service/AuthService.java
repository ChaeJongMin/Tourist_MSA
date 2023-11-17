package com.auth.authservice.Service;

import com.auth.authservice.Domain.RefreshTokenEntity;
import com.auth.authservice.Dto.ResponseTokenDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    //access 발급

    String generateAccess(String userId, HttpServletResponse response);

    //refresh 발급
    String generateRefresh();


    String saveRefreshToken(String userId, HttpServletResponse response);

    ResponseTokenDto generateAllTokens(String userId, HttpServletResponse response);


    String findUserIdByToken(String value);
}
