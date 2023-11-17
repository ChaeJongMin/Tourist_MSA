package com.gateway.apigateway.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="auth-service")
public interface ReissueClient {
    @GetMapping("/auth-service/api/reissue")
    String tokenReissue();
}
