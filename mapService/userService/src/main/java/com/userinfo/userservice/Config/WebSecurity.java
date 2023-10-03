package com.userinfo.userservice.Config;

import com.userinfo.userservice.Security.AuthenticationProvider.CustomAuthenticationProvider;
import com.userinfo.userservice.Security.Filter.CustomLoginFilter;
import com.userinfo.userservice.Security.Handler.FailureHandler;
import com.userinfo.userservice.Security.Handler.SuccessHandler;
import com.userinfo.userservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurity {

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(getCustomAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }
    @Bean
    public CustomAuthenticationProvider getCustomAuthenticationProvider(){
        return new CustomAuthenticationProvider(userService,bCryptPasswordEncoder);
    }
    @Bean
    public SuccessHandler getSuccessHandler(){
        return new SuccessHandler(userService);
    }
    @Bean
    public FailureHandler getFailureHandler(){
        return new FailureHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests.requestMatchers("/tourist/login").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().permitAll()
                )
                .addFilterAfter(getCustomLoginFilter(authManager(http)), LogoutFilter.class);

        return http.build();

    }
        @Bean
        public CustomLoginFilter getCustomLoginFilter(AuthenticationManager authenticationManager){
            CustomLoginFilter customLoginFilter=new CustomLoginFilter();
            customLoginFilter.setAuthenticationManager(authenticationManager);
            customLoginFilter.setAuthenticationFailureHandler(getFailureHandler());
            customLoginFilter.setAuthenticationSuccessHandler(getSuccessHandler());
            return customLoginFilter;
        }
}
