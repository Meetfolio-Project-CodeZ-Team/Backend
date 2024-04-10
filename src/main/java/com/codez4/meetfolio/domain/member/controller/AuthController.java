package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.member.dto.LoginRequest;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.jwt.JwtProperties;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인/로그아웃 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final MemberQueryService memberQueryService;

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request){
        String jwtToken = memberQueryService.login(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX + jwtToken);
        return ResponseEntity.ok().headers(headers).body(ApiResponse.onSuccess("로그인에 성공하였습니다."));
    }

}
