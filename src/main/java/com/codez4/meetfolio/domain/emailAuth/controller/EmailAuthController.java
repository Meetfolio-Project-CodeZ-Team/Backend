package com.codez4.meetfolio.domain.emailAuth.controller;

import com.codez4.meetfolio.domain.emailAuth.dto.EmailAuthRequest;
import com.codez4.meetfolio.domain.emailAuth.dto.EmailRequest;
import com.codez4.meetfolio.domain.emailAuth.service.EmailAuthCommandService;
import com.codez4.meetfolio.domain.emailAuth.service.EmailAuthQueryService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
public class EmailAuthController {

    private final EmailAuthQueryService emailAuthQueryService;
    private final EmailAuthCommandService emailAuthCommandService;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "이메일 인증 코드 전송 요청", description = "입력한 이메일로 인증 코드를 전송합니다.")
    @PostMapping
    public ApiResponse<String> sendAuthCode(@Valid @RequestBody EmailRequest request) {
        memberQueryService.checkDuplicatedEmail(request.getEmail());
        emailAuthCommandService.sendEmail(request.getEmail());
        return ApiResponse.onSuccess("이메일 전송이 완료되었습니다.");
    }

    @Operation(summary = "가천대 재학생 인증 요청", description = "가천대 재학생 인증을 요청합니다.")
    @PostMapping("/authentication")
    public ApiResponse<String> verifyAuthCode(@Valid @RequestBody EmailAuthRequest request) {
        String result = emailAuthQueryService.verifyAuthCode(request);
        return ApiResponse.onSuccess(result);
    }

}
