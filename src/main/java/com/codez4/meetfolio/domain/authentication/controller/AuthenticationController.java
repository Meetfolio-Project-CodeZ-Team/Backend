package com.codez4.meetfolio.domain.authentication.controller;

import com.codez4.meetfolio.domain.authentication.dto.AuthenticationRequest;
import com.codez4.meetfolio.domain.authentication.dto.EmailRequest;
import com.codez4.meetfolio.domain.authentication.service.AuthenticationCommandService;
import com.codez4.meetfolio.domain.authentication.service.AuthenticationQueryService;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "이메일 인증 API")
@RestController
@RequestMapping("/api/signup/email")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationQueryService authenticationQueryService;
    private final AuthenticationCommandService authenticationCommandService;

    @Operation(summary = "이메일 인증 코드 전송 요청", description = "입력한 이메일로 인증 코드를 전송합니다.")
    @PostMapping
    public ApiResponse<String> sendAuthCode(@RequestBody EmailRequest request) {
        authenticationCommandService.sendEmail(request.getEmail());
        return ApiResponse.onSuccess("이메일 전송이 완료되었습니다.");
    }

    @Operation(summary = "가천대 재학생 인증 요청", description = "가천대 재학생 인증을 요청합니다.")
    @PostMapping("/authentication")
    public ApiResponse<String> verifyAuthCode(@RequestBody AuthenticationRequest request) {
        String result = authenticationQueryService.verifyAuthCode(request);
        return ApiResponse.onSuccess(result);
    }

}
