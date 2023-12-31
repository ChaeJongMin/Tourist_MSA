package com.userinfo.userservice.Security.Filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

//사용 안함 나중에 따로 처리 필요!!
@Slf4j
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/user-service/api/auth/login" ;
    private static final String HTTP_METHOD = "POST";
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "passwd";
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    public CustomLoginFilter(){
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("user-service 로그인 필터 작동");
        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

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
    }

}
