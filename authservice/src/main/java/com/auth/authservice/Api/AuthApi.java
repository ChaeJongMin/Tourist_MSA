package com.auth.authservice.Api;

import com.auth.authservice.Dto.ResponseTokenDto;
import com.auth.authservice.Service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth-service")
@Slf4j
public class AuthApi {

    private final AuthService authService;

    //로그인 시 토큰 발급
    @GetMapping("/api/issue/{userId}")
    public ResponseEntity<ResponseTokenDto> respondAllToken(HttpServletResponse response, @PathVariable String userId){
        log.info("/api/issue/{userId} 작동 : "+ userId);
        return ResponseEntity.status(HttpStatus.OK).body(authService.generateAllTokens(userId, response));
    }
    //1.액세스 토큰 재발급
    @GetMapping("/api/reissue")
    public ResponseEntity<String> respondAccessToken(HttpServletResponse response, @CookieValue(name = "refreshtoken") String refreshValue){
        if (refreshValue == null || refreshValue.isEmpty()) {
            //클라이언트에서 따로 처리하거나 게이트웨이에서 처리하기 위해 body 필요
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no RefreshToken");
        }
        log.info("api/reissue - 리프레쉬 토큰 값 : "+refreshValue);
        String userId = authService.findUserIdByToken(refreshValue);
        return ResponseEntity.status(HttpStatus.OK).body(authService.generateAccess(userId, response));
    }


}
