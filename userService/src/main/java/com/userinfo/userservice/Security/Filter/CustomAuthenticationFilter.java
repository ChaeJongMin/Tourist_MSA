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

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "passwd";

    private final UserService userService;

    private final AuthServiceCilent authServiceCilent;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("user-service 로그인 필터 작동");

        String messageBody = null;
        try {
            messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> usernamePasswordMap = new Gson().fromJson(messageBody, mapType);

            String email = usernamePasswordMap.get(EMAIL_KEY);

            email = (email != null) ? email : "";
            email = email.trim();

            String userPasswd = usernamePasswordMap.get(PASSWORD_KEY);
            userPasswd = (userPasswd != null) ? userPasswd : "";
            userPasswd = userPasswd.trim();
            log.info("CustomLoginFilter : 받아온 아이디  "+ email);
            log.info("CustomLoginFilter : 받아온 비밀번호  "+ userPasswd);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, userPasswd);

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("user-service 성공 핸들러 작동");
        String email=authentication.getPrincipal().toString();

        String name=userService.getUserNameByEmail(email);

        //인증 인스턴스에서 토큰을 받아와야한다.
        log.info("인증 인스턴스에서 토큰을 받아올게요");
        ResponseCookieDto responseCookieDto=authServiceCilent.respondAllToken(name);
        generateCookie(responseCookieDto, response);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HashMap<String, String> successMap=new HashMap<>();
        successMap.put("results",name);

        String result=new Gson().toJson(successMap);

        response.getWriter().write(result);
    }

    public void generateCookie(ResponseCookieDto responseCookieDto, HttpServletResponse response){
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
    }

}
