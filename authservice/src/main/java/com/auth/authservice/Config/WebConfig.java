package com.auth.authservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // cors설정을 위해 사용
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://host.docker.internal:8000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
