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
//헤더에 담긴 액세스토큰 쿠키를 가져와 검사하는 필터
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;

    private final ReissueClient reissueClient;

    private String erroMsg="";
    private final Key key;

    // 의존성을 관리하는 생성자
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

            //액세스토큰을 가져와 값을 추출
            String jwt = resolveAccessTokenFromRequest(request, "accesstoken");
            // 비어 있을 경우
            if (jwt == null) {
                log.info("AuthorizationHeaderFilter : 헤더에 토큰이 존재하지 않습니다.");
                //새로 액세스 토큰을 받기위해 FeignClient로 Auth-Service의 재발급 API 요청
                String refreshValue = resolveAccessTokenFromRequest(request, "refreshtoken");
                String refresh = reissueClient.tokenReissue(refreshValue);
                //액세스 토큰을 쿠키에 집어넣어 응답
                generateCookie(refresh,response);
            }
            // 액세스 토큰 값이 유효하지 않을 경우
            if (!isJwtValid(jwt)) {
                //만약 만료 시
                if(erroMsg.equals("만료된 JWT 토큰입니다.")){
                    log.info("AuthorizationHeaderFilter : 액세스 토큰 만료");
                    //새로 액세스 토큰을 받기위해 FeignClient로 Auth-Service의 재발급 API 요청
                    String refreshValue = resolveAccessTokenFromRequest(request, "refreshtoken");
                    String refresh = reissueClient.tokenReissue(refreshValue);
                    generateCookie(refresh,response);
                }
                else {
                    //그 외 상황은 에러 응답
                    return onError(exchange, erroMsg, HttpStatus.UNAUTHORIZED);
                }
            }

            return chain.filter(exchange);
        };
    }
    //잘못된 상황일 경우 클라이언트에게 40X 응답 전송하는 메소드
    //Reactor의 Mono를 사용 (Spring WebFlux 프레임워크)
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    public static class Config {

    }
    //토큰 유효성을 검사하는 메소드
    private boolean isJwtValid(String token){
        try {
            //JWT을 파싱하여 그 내용을 추출
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

    // 쿠키로부터 "accesstoken" key가 있는지 확인하고 있을 시 value를 반환하는 메소드
    private String resolveAccessTokenFromRequest(ServerHttpRequest request, String cookieKey) {

        HttpCookie accessTokenCookie = request.getCookies().getFirst(cookieKey);
        if (accessTokenCookie != null) {
            return accessTokenCookie.getValue();
        } else {
            return null;
        }
    }
    //쿠키 생성하는 메소드
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
