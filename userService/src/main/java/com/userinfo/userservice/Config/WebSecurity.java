package com.userinfo.userservice.Config;

import com.userinfo.userservice.Client.AuthServiceCilent;
import com.userinfo.userservice.Security.AuthenticationProvider.CustomAuthenticationProvider;
import com.userinfo.userservice.Security.Filter.CustomAuthenticationFilter;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;


import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class WebSecurity {

    private final UserService userService;
    private final AuthServiceCilent authServiceCilent;

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
        return new SuccessHandler(userService,authServiceCilent);
    }
    @Bean
    public FailureHandler getFailureHandler(){
        return new FailureHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("http://host.docker.internal:8000"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L); //1시간
                    return config;
                }))
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .anyRequest().permitAll()
                )
                .addFilterAfter(getCustomAuthenticationFilter(authManager(http)), LogoutFilter.class);

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
        @Bean
        public CustomAuthenticationFilter getCustomAuthenticationFilter(AuthenticationManager authenticationManager) {
            CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(userService, authServiceCilent);
            customAuthenticationFilter.setAuthenticationManager(authenticationManager);
            return customAuthenticationFilter;
        }
}
