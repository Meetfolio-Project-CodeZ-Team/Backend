package com.codez4.meetfolio.global.config;

import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.annotation.AuthenticationMemberArgumentResolver;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationMemberArgumentResolver(jwtTokenProvider, memberRepository));
    }
}
