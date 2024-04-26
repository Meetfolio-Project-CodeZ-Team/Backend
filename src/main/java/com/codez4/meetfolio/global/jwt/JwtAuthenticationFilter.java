package com.codez4.meetfolio.global.jwt;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisUtil redisUtil;

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
            || request.getMethod().equals("GET") && request.getServletPath()
            .contains("/api/experiences")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractAccessToken(request);
        String refreshToken = extractRefreshToken(request);
        if (StringUtils.hasText(accessToken)) {
            if (redisUtil.hasKeyBlackList(accessToken)) {
                throw new ApiException(ErrorStatus._LOGOUT_USER);
            } else if (jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                boolean isValidated = jwtTokenProvider.validateToken(refreshToken);
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);
                if (isRefreshToken && isValidated) {
                    Long memberId = jwtTokenProvider.getUserId(accessToken);
                    Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND));
                    String newAccessToken = jwtTokenProvider.generateAccessToken(member.getEmail(),
                        memberId, member.getAuthority());
                    jwtTokenProvider.setHeaderAccessToken(response,
                        JwtProperties.TOKEN_PREFIX + newAccessToken);
                    SecurityContextHolder.getContext()
                        .setAuthentication(jwtTokenProvider.getAuthentication(newAccessToken));
                }
            } else {
                filterChain.doFilter(request, response);
                return;
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new JwtException("인증 정보가 Security Context에 존재하지 않습니다.");
        }

        Claims claims = jwtTokenProvider.parseClaims(accessToken);
        Long memberId = jwtTokenProvider.getUserId(accessToken);
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND));
        if (member == null || !member.getId().equals(claims.get("id", Long.class))) {
            throw new JwtException("토큰 값의 유저 정보가 올바르지 않습니다.");
        }

        filterChain.doFilter(request, response);
    }

    public static String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.ACCESS_HEADER_STRING);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(
            JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String extractRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.REFRESH_HEADER_STRING);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(
            JwtProperties.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
