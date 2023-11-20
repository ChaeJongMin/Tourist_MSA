package com.userinfo.userservice.Security.Handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
//사용 안함 나중에 따로 처리 필요!!
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler  {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String error = "";
        if(exception instanceof UsernameNotFoundException){
            error=exception.getMessage();
        } else if(exception instanceof BadCredentialsException){
            error=exception.getMessage();
        } else {
            error="Invalid Error... Sorry";
        }

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        HashMap<String, String> errorMap=new HashMap<>();
        errorMap.put("results",error);

        String result=new Gson().toJson(errorMap);

        response.getWriter().write(result);
    }
}
