package com.codez4.meetfolio.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://34.64.177.41:60005", "http://meetfolio.kro.kr:60005", "http://34.64.177.41:3000",
                "http://meetfolio.kro.kr:60005", "http://localhost:3000")
            .allowedMethods(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name())
            .allowCredentials(true)
            .maxAge(3600)
            .exposedHeaders("Authorization", "RefreshToken");
    }

}
