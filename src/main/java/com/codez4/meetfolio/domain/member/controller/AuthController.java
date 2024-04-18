package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.LoginRequest;
import com.codez4.meetfolio.domain.member.dto.LoginTokenResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.domain.member.service.AuthService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.jwt.JwtAuthenticationFilter;
import com.codez4.meetfolio.global.jwt.JwtProperties;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberInfo;

@Slf4j
@Tag(name = "로그인/로그아웃 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final MemberQueryService memberQueryService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인", description = "로그인, 성공 시 response header access token과 refresh 토큰을, body에  로그인한 사용자 정보를 반환합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponse.MemberInfo>> login(@Valid @RequestBody LoginRequest request,
                                                                        HttpServletResponse response) {
        Member member = memberQueryService.checkEmailAndPassword(request);
        LoginTokenResponse tokenResponse = authService.authorize(member);
        jwtTokenProvider.setHeaderAccessToken(response, tokenResponse.getAccessToken());
        jwtTokenProvider.setHeaderRefreshToken(response, tokenResponse.getRefreshToken());
        return ResponseEntity.ok().body(ApiResponse.onSuccess(toMemberInfo(member)));
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @PostMapping("/logout")
    public String logout(@AuthenticationMember Member member,
                         HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            response.setHeader(JwtProperties.ACCESS_HEADER_STRING, "");
            String accessToken = JwtAuthenticationFilter.extractAccessToken(request);
            String refreshToken = JwtAuthenticationFilter.extractRefreshToken(request);
            authService.logout(accessToken, refreshToken);
            ;
        }
        return "redirect:/main";
    }

}
