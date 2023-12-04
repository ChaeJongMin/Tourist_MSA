package com.auth.authservice.Api;

import com.auth.authservice.Dto.ResponseTokenDto;
import com.auth.authservice.Service.AuthService;
import io.micrometer.core.annotation.Timed;
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
    //user-service에서 로그인 성공 시 FeginClient로 해당 아래 api 요청
    @GetMapping("/api/issue/{userId}")
    @Timed(value = "auth-issue" , longTask = true)
    public ResponseEntity<ResponseTokenDto> respondAllToken(HttpServletResponse response, @PathVariable String userId){
        log.info("/api/issue/{userId} 작동 : "+ userId);
        return ResponseEntity.status(HttpStatus.OK).body(authService.generateAllTokens(userId, response));
    }
    //액세스 토큰 만료 시 리프레쉬 토큰으로 액세스 토큰을 재발급
    //Apigateway의 토큰 검사 필터에서 만료 or 사라짐 일떄 FeginClient로 해당 아래 api 요청
    @GetMapping("/api/reissue")
    @Timed(value = "auth-reissue" , longTask = true)
    public ResponseEntity<String> respondAccessToken(HttpServletResponse response,String refreshValue){
        //리프레쉬 토큰의 비어있을 시
        if (refreshValue == null || refreshValue.isEmpty()) {
            //예외처리 발동 ( 클라이언트에 응답하기 위해 따로 처리할 필요 있음)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "no RefreshToken");
        }
        log.info("api/reissue - 리프레쉬 토큰 값 : "+refreshValue);
        String userId = authService.findUserIdByToken(refreshValue);
        return ResponseEntity.status(HttpStatus.OK).body(authService.generateAccess(userId, response));
    }


}
