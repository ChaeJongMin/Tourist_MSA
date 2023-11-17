package com.userinfo.userservice.Client;

import com.userinfo.userservice.Dto.Response.ResponseCookieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="auth-service")
public interface AuthServiceCilent {
    @GetMapping("/auth-service/api/issue/{userId}")
    ResponseCookieDto respondAllToken(@PathVariable("userId") String userId);
}
