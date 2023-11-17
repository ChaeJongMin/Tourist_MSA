//package com.userinfo.userservice.Config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://host.docker.internal:8000")
//                .allowedMethods("GET", "POST", "PUT", "DELETE",  "OPTIONS")
//                .maxAge(3600);
//    }
//
//}
