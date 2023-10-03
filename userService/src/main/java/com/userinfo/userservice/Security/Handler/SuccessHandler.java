package com.userinfo.userservice.Security.Handler;

import com.google.gson.Gson;
import com.userinfo.userservice.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String email=authentication.getPrincipal().toString();

        //인증 인스턴스에서 토큰을 받아와야한다.

        String name=userService.getUserNameByEmail(email);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HashMap<String, String> successMap=new HashMap<>();
        successMap.put("results",name);

        String result=new Gson().toJson(successMap);

        response.getWriter().write(result);
        
    }
}
