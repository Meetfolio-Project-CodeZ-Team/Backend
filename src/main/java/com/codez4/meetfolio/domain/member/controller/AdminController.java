package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원 목록 조회", description = "회원관리 메뉴의 회원 목록을 조회합니다.")
    @GetMapping("/members-management")
    public ApiResponse<MemberResponse.MemberList> getMemberList(@AuthenticationMember Member admin,
                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "jobKeyword", required = false) String jobKeyword) {
        JobKeyword jobKeywordEnum;
        if (jobKeyword != null) {
            jobKeywordEnum = JobKeyword.convert(jobKeyword);
            if (jobKeywordEnum == null) throw new ApiException(ErrorStatus._BAD_REQUEST);
        } else jobKeywordEnum = null;
        return ApiResponse.onSuccess(memberQueryService.getMemberList(page, jobKeywordEnum));
    }

    @Operation(summary = "회원 비활성화", description = "회원관리 메뉴에서 회원을 비활성화합니다.")
    @Parameter(name = "memberId", description = "회원 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @DeleteMapping("/members-management")
    public ApiResponse<String> deleteMember(@AuthenticationMember Member admin,
                                            @PathVariable(value = "memberId") Long memberId) {
        Member member = memberQueryService.findById(memberId);
        memberCommandService.inactivateMember(member);
        return ApiResponse.onSuccess("회원 비활성화 성공입니다.");
    }

}
