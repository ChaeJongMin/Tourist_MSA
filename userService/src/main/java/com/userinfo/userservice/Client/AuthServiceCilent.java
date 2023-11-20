package com.userinfo.userservice.Client;

import com.userinfo.userservice.Dto.Response.ResponseCookieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//FeiginClient로 auth 인스턴스의 API 요청하기 위한 인터페이스
@FeignClient(name="auth-service")
public interface AuthServiceCilent {
    //액세스, 리프레쉬 토큰 발급 API
    @GetMapping("/auth-service/api/issue/{userId}")
    ResponseCookieDto respondAllToken(@PathVariable("userId") String userId);
}
