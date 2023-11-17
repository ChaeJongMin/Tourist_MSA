package com.gateway.apigateway.Filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CheckTokenFilter extends AbstractGatewayFilterFactory<CheckTokenFilter.Config> {
    public CheckTokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(CheckTokenFilter.Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("CheckReissueFilter Filter baseMessage: {}, {}", config.getBaseMessage(), request.getRemoteAddress());
            if (config.isPreLogger()) {
                log.info("CheckReissueFilter Filter Start: request id -> {}", request.getId());
            }
            if(!response.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                //예외처리 필요
                log.info("PreLogger - 액세스 토큰이 비어있습니다.");
            }

            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    log.info("CheckReissueFilter Filter End: response code -> {}", response.getStatusCode());
                }
                if(!response.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    //예외처리 필요
                    log.info("PostLogger - 액세스 토큰이 비어있습니다.");
                } else {
                    String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                    String jwt = authorizationHeader.replace("Bearer ", "");
                    log.info("PostLogger - 액세스 토큰 : "+jwt);
                }

            }));
        });
    }
    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
