package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Major;
import com.codez4.meetfolio.domain.member.dto.LoginRequest;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.jwt.JwtProperties;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @PostMapping("/signup")
    public ApiResponse<MemberResponse.MemberProc> signUp(@RequestBody MemberRequest.SignUpRequest request) {
        MemberRequest.Post post = MemberRequest.Post.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .grade(Grade.convert(request.getGrade()))
                .jobKeyword(JobKeyword.convert(request.getJobKeyword()))
                .major(Major.convert(request.getMajor()))
                .build();
        return ApiResponse.onSuccess(memberCommandService.post(post));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request){
        String jwtToken = memberQueryService.login(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX + jwtToken);
        return ResponseEntity.ok().headers(headers).body("로그인 성공");
    }

//    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정")
//    @PatchMapping("/my-info")
//    public ApiResponse<MemberResponse.MemberProc> updateMemberInfo()
}
