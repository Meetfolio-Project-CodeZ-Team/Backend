package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Major;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원 가입", description = "회원 가입")
    @PostMapping("/signup")
    public ApiResponse<MemberResponse.MemberProc> signUp(
        @Valid @RequestBody MemberRequest.SignUpRequest request) {
        MemberRequest.Post post = MemberRequest.Post.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .grade(Grade.convert(request.getGrade()))
            .jobKeyword(JobKeyword.convert(request.getJobKeyword()))
            .major(Major.convert(request.getMajor()))
            .build();
        return ApiResponse.onSuccess(memberCommandService.post(post));
    }

    @Operation(summary = "마이페이지 수정 정보 조회", description = "사용자의 개인 정보를 조회합니다.")
    @GetMapping("/mypage")
    public ApiResponse<MemberResponse.MemberDetailInfo> myPage(
        @AuthenticationMember Member member) {

        return ApiResponse.onSuccess(memberQueryService.getMyPage(member));
    }

    @Operation(summary = "마이페이지 수정 요청", description = "사용자의 개인 정보를 수정합니다.")
    @PatchMapping("/mypage")
    public ApiResponse<MemberResponse.MemberProc> editMyPage(@AuthenticationMember Member member,
        @RequestBody MemberRequest.Patch patch) {

        return ApiResponse.onSuccess(memberCommandService.update(member, patch));
    }

}
