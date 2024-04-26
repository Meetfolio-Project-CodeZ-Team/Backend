package com.codez4.meetfolio.global.annotation;

import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.jwt.JwtAuthenticationFilter;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthenticationMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationMember.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String accessToken = JwtAuthenticationFilter.extractAccessToken(request);

        // 비로그인 / 로그인 경로 예외
        if ((request.getServletPath().equals("/api") || request.getServletPath()
            .contains("/api/experiences"))
            && accessToken == null) {
            return null;
        }

        if (accessToken.isEmpty() || accessToken.isBlank()) {
            throw new ApiException(ErrorStatus._FORBIDDEN);
        }

        validateTokenIntegrity(accessToken);

        Long memberId = jwtTokenProvider.getUserId(accessToken);

        return memberRepository.findById(memberId)
            .orElseThrow(() -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND));
    }

    private void validateTokenIntegrity(String token) {
        jwtTokenProvider.validateToken(token);
    }
}
