package com.codez4.meetfolio.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cors = new CorsConfiguration();
        // TODO: 추후 Public IP로 변경
        cors.setAllowedOrigins(List.of("https://localhost:3000", "http://localhost:8080"));
        cors.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
        cors.setAllowCredentials(true); // cookie 활성화
        cors.addExposedHeader("Authorization"); // Authorization Header 노출
        source.registerCorsConfiguration("/**", cors);
        return new CorsFilter(source);
    }
}
