package com.userinfo.userservice.Security.Filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.userinfo.userservice.Client.AuthServiceCilent;
import com.userinfo.userservice.Dto.Response.ResponseCookieDto;
import com.userinfo.userservice.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//커스텀 로그인 필터 (로그인시 작동) spring security에서 제공한 로그인 default 주소로 요청이 오면 작동
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "passwd";

    private final UserService userService;

    private final AuthServiceCilent authServiceCilent;

    //인증 시도하는 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("user-service 로그인 필터 작동");

        String messageBody = null;
        try {
            //HTTP 요청의 본문(body)을 문자열로 읽어오기
            //request.getInputStream()를 통해 본문을 byte stream 형태로 가져오기
            //copyToString메소드로 바이트 스트림을 StandardCharsets.UTF_8으로 인코딩하여 문자열로 변경
            messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            //문자열(JSON)을 Map 객체로 변환
            Map<String, String> usernamePasswordMap = new Gson().fromJson(messageBody, mapType);
            //email 추출
            String email = usernamePasswordMap.get(EMAIL_KEY);

            email = (email != null) ? email : "";
            email = email.trim();
            //비밀번호 추출
            String userPasswd = usernamePasswordMap.get(PASSWORD_KEY);
            userPasswd = (userPasswd != null) ? userPasswd : "";
            userPasswd = userPasswd.trim();
            log.info("CustomLoginFilter : 받아온 아이디  "+ email);
            log.info("CustomLoginFilter : 받아온 비밀번호  "+ userPasswd);
            //AuthenticationManger에게 토큰 전달
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, userPasswd);

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //로그인 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("user-service 성공 핸들러 작동");
        String email=authentication.getPrincipal().toString();
        //이메일로부터 유저 네임 획득
        String name=userService.getUserNameByEmail(email);

        //인증 인스턴스에서 토큰을 받아와야한다.
        log.info("인증 인스턴스에서 토큰을 받아올게요");
        //auth 서비스한테 FeginClient를 사용하여 토큰 발급 API를 호출하여 토큰 받아오기
        ResponseCookieDto responseCookieDto=authServiceCilent.respondAllToken(name);
        //받아온 토큰으로 쿠키에 저장
        generateCookie(responseCookieDto, response, name);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HashMap<String, String> successMap=new HashMap<>();
        successMap.put("results",name);

        String result=new Gson().toJson(successMap);

        response.getWriter().write(result);
    }

    public void generateCookie(ResponseCookieDto responseCookieDto, HttpServletResponse response, String name){
        Cookie accessCookie = new Cookie("accesstoken", responseCookieDto.getAccess());
        accessCookie.setPath("/");
        accessCookie.setHttpOnly(true);
        accessCookie.setMaxAge(1800000);
        response.addCookie(accessCookie);

        // refresh 쿠키 생성
        Cookie refreshCookie = new Cookie("refreshtoken", responseCookieDto.getRefresh());
        refreshCookie.setPath("/");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(86400000);
        response.addCookie(refreshCookie);

        Cookie nameCookie = new Cookie("useridCookie", name);
        nameCookie.setPath("/");
        nameCookie.setMaxAge(86400000);
        response.addCookie(nameCookie);

    }

}
