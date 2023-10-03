package com.userinfo.userservice.Security.AuthenticationProvider;

import com.userinfo.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;

        String name=authToken.getName();

        Optional.ofNullable(name).orElseThrow(
                () -> new UsernameNotFoundException("아이디와 비밀번호가 잘못되었습니다."));
        UserDetails userDetails=userService.loadUserByUsername(name);
        String passwd=userDetails.getPassword();

        if(!passwordEncoder.matches((String)authToken.getCredentials(),passwd)){
            throw new BadCredentialsException("Invalid Username / Password");
        }

        return new UsernamePasswordAuthenticationToken(name, passwd, new ArrayList<>());
    }
    //Username/Password 방식의 인증을 지원한다는 정보전달
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
