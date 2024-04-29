package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.LoginRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.TokenResponse;
import com.codez4.meetfolio.domain.member.service.AuthService;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberInfo;

@Slf4j
@Tag(name = "로그인/로그아웃 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "로그인", description = "로그인, 성공 시 response header에 access 토큰과 refresh 토큰을, body에  로그인한 사용자 정보를 반환합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResponse.MemberInfo>> login(@Valid @RequestBody LoginRequest request,
                                                                        HttpServletResponse response) {
        Member member = memberQueryService.checkEmailAndPassword(request);
        TokenResponse tokenResponse = authService.authorize(member);
        jwtTokenProvider.setHeaderAccessToken(response, JwtProperties.TOKEN_PREFIX + tokenResponse.getAccessToken());
        jwtTokenProvider.setHeaderRefreshToken(response, JwtProperties.TOKEN_PREFIX + tokenResponse.getRefreshToken());
        return ResponseEntity.ok().body(ApiResponse.onSuccess(toMemberInfo(member)));
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @DeleteMapping("/logout")
    public String logout(@AuthenticationMember Member member,
                         HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String accessToken = JwtAuthenticationFilter.extractAccessToken(request);
            String refreshToken = JwtAuthenticationFilter.extractRefreshToken(request);
            authService.logout(accessToken, refreshToken);
            jwtTokenProvider.setHeaderAccessToken(response, "");
            jwtTokenProvider.setHeaderRefreshToken(response, "");
            ;
        }
        return "redirect:/main";
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    @DeleteMapping("/withdrawl")
    public ResponseEntity withDrawl(@AuthenticationMember Member member, HttpServletRequest request) {
        String accessToken = JwtAuthenticationFilter.extractAccessToken(request);
        String refreshToken = JwtAuthenticationFilter.extractRefreshToken(request);
        authService.withdraw(member, accessToken, refreshToken);
        return ResponseEntity.ok().body("회원탈퇴가 완료되었습니다.");
    }

}
