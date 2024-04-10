package com.codez4.meetfolio.global.jwt;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/login")
                || request.getServletPath().contains("/api/signup")
                || request.getServletPath().contains("/swagger-ui")
                || request.getServletPath().contains("/swagger-resources")
                || request.getServletPath().contains("v3/api-docs")
                || request.getServletPath().equals("/api")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = extractToken(request);
        Long userId = jwtTokenProvider.getUserId(jwt);
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND));

        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new JwtException("인증 정보가 Security Context에 존재하지 않습니다.");
        }

        Claims claims = jwtTokenProvider.parseClaims(jwt);
        if (member == null || !member.getId().equals(claims.get("id", Long.class))) {
            throw new JwtException("토큰 값의 유저 정보가 올바르지 않습니다.");
        }

        filterChain.doFilter(request, response);
    }

    public static String resolveToken(HttpServletRequest request) {
        return extractToken(request);
    }

    private static String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.HEADER_STRING);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
