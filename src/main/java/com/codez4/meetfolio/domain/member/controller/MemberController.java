package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Major;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @PostMapping("/signup")
    public ApiResponse<MemberResponse.MemberProc> signUp(@Valid @RequestBody MemberRequest.SignUpRequest request) {
        MemberRequest.Post post = MemberRequest.Post.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .grade(Grade.convert(request.getGrade()))
                .jobKeyword(JobKeyword.convert(request.getJobKeyword()))
                .major(Major.convert(request.getMajor()))
                .build();
        return ApiResponse.onSuccess(memberCommandService.post(post));
    }

}
