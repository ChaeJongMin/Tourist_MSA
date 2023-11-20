package com.userinfo.userservice.Security.AuthenticationProvider;

import com.userinfo.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

// 커스텀한 AuthenticationProvider 클래스
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //AuthenticationManager 받은 authToken을 통해 로그인한 아이디를 추출
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String name=authToken.getName();

        //name이 널인지 확인
        Optional.ofNullable(name).orElseThrow(
                () -> new UsernameNotFoundException("아이디와 비밀번호가 잘못되었습니다."));
        //loadUserByUsername 메소드로 userDetails 객체 반환
        UserDetails userDetails=userService.loadUserByUsername(name);
        String passwd=userDetails.getPassword();
        log.info("AuthenticationProvider 비밀번호 : "+ (String)authToken.getCredentials());
        //비밀번호 비교
        if(!passwordEncoder.matches((String)authToken.getCredentials(),passwd)){
            //틀릴 시 예외처리
            throw new BadCredentialsException("Invalid Username / Password");
        }
        //인증된 UsernamePasswordAuthenticationToken 반환
        return new UsernamePasswordAuthenticationToken(name, passwd, new ArrayList<>());
    }
    //Username/Password 방식의 인증을 지원한다는 정보전달
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
