package com.gateway.apigateway.Filter;

import com.gateway.apigateway.Client.ReissueClient;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Arrays;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    private final ReissueClient reissueClient;

    private String erroMsg="";
    private final Key key;
    public AuthorizationHeaderFilter(@Lazy ReissueClient reissueClient, Environment env) {
        super(Config.class);
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("token.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.reissueClient=reissueClient;
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String url = request.getURI().toString();
            log.info("AuthorizationHeaderFilter Requested URL: {}", url);

            String jwt = resolveAccessTokenFromRequest(request);
            if (jwt == null) {
                log.info("AuthorizationHeaderFilter : 헤더에 토큰이 존재하지 않습니다.");
                String refresh = reissueClient.tokenReissue();
                generateCookie(refresh,response);
            }

            if (!isJwtValid(jwt)) {
                if(erroMsg.equals("만료된 JWT 토큰입니다.")){
                    log.info("AuthorizationHeaderFilter : 액세스 토큰 만료");
                    String refresh = reissueClient.tokenReissue();
                }
                else {
                    return onError(exchange, erroMsg, HttpStatus.UNAUTHORIZED);
                }
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    public static class Config {

    }

    private boolean isJwtValid(String token){
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            erroMsg="잘못된 JWT 서명입니다.";
        } catch (ExpiredJwtException e) {
            erroMsg="만료된 JWT 토큰입니다.";
        } catch (UnsupportedJwtException e) {
            erroMsg="지원되지 않는 JWT 토큰입니다.";
        } catch (IllegalArgumentException e) {
            erroMsg="JWT 토큰이 잘못되었습니다.";
        }
        return false;
    }
    private String resolveAccessTokenFromRequest(ServerHttpRequest request) {
        HttpCookie accessTokenCookie = request.getCookies().getFirst("accesstoken");
        if (accessTokenCookie != null) {
            return accessTokenCookie.getValue();
        } else {
            return null;
        }
    }
    public void generateCookie(String refresh, ServerHttpResponse response){
        // refresh 쿠키 생성
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refresh)
                .path("/")
                .httpOnly(true)
                .maxAge(86400000)
                .build();
        response.addCookie(refreshCookie);
    }
}
