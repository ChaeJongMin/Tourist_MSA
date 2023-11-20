package com.userinfo.userservice.Security.Handler;

import com.google.gson.Gson;
import com.userinfo.userservice.Client.AuthServiceCilent;
import com.userinfo.userservice.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
@Slf4j
//사용 안함 나중에 따로 처리 필요!!
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final AuthServiceCilent authServiceCilent;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("user-service 성공 핸들러 작동");
        String email=authentication.getPrincipal().toString();

        String name=userService.getUserNameByEmail(email);

        //인증 인스턴스에서 토큰을 받아와야한다.
        log.info("인증 인스턴스에서 토큰을 받아올게요");
//        authServiceCilent.respondAllToken(response,name);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HashMap<String, String> successMap=new HashMap<>();
        successMap.put("results",name);

        String result=new Gson().toJson(successMap);

        response.getWriter().write(result);
        
    }
}
