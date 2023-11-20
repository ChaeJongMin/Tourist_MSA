package com.gateway.apigateway.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// FeginClient로 외부 서비스의 API를 호출하기 위한 인터페이스
@FeignClient(name="auth-service")
public interface ReissueClient {
    //리프레쉬 토큰으로부터 액세스 토큰을 받아오는 API
    @GetMapping("/auth-service/api/reissue")
    String tokenReissue();
}
