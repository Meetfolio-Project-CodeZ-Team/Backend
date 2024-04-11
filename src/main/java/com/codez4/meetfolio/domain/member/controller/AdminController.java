package com.codez4.meetfolio.domain.member.controller;

import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberCommandService;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.domain.payment.dto.PaymentResponse;
import com.codez4.meetfolio.domain.payment.service.PaymentQueryService;
import com.codez4.meetfolio.domain.point.dto.PointResponse;
import com.codez4.meetfolio.domain.point.service.PointQueryService;
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

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final PointQueryService pointQueryService;
    private final PaymentQueryService paymentQueryService;

    @Operation(summary = "회원 목록 조회", description = "회원 관리 메뉴의 회원 목록을 조회합니다.")
    @GetMapping("/members-management")
    public ApiResponse<MemberResponse.MemberListResult> getMemberList(@AuthenticationMember Member admin,
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

    @Operation(summary = "포인트 사용 통계", description = "매출 관리 메뉴에서 포인트 사용 통계를 조회합니다. year, month 모두 입력 안 할 시 현재 달의 통계를 전송합니다. year, month 중 하나만 입력 시 에러를 반환합니다.")
    @Parameter(name = "year", description = "년도", required = false, example = "2024", in = ParameterIn.QUERY)
    @Parameter(name = "month", description = "월", required = false, example = "4", in = ParameterIn.QUERY)
    @GetMapping("/point-management")
    public ApiResponse<PointResponse.PointStatics> getPointStatics(@AuthenticationMember Member admin,
                                                                   @RequestParam(required = false) Integer year,
                                                                   @RequestParam(required = false) Integer month) {
        if (year == null && month == null) {
            return ApiResponse.onSuccess(pointQueryService.getPointStatics(LocalDate.now(ZoneId.of("Asia/Seoul")).getYear(), LocalDate.now(ZoneId.of("Asia/Seoul")).getMonthValue()));
        } else if (year != null && month != null)
            return ApiResponse.onSuccess(pointQueryService.getPointStatics(year, month));
        else throw new ApiException(ErrorStatus._BAD_REQUEST);
    }

    @Operation(summary = "결제 내역 조회", description = "매출 관리 메뉴에서 결제 내역을 조회합니다. year, month 모두 입력 안 할 시 현재 달의 통계를 전송합니다. year, month 중 하나만 입력 시 에러를 반환합니다.")
    @Parameter(name = "year", description = "년도", required = false, example = "2024", in = ParameterIn.QUERY)
    @Parameter(name = "month", description = "월", required = false, example = "4", in = ParameterIn.QUERY)
    @GetMapping("/payment-management")
    public ApiResponse<PaymentResponse.PaymentResult> getPaymentList(@AuthenticationMember Member admin,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(required = false) Integer year,
                                                                     @RequestParam(required = false) Integer month) {
        if (year == null && month == null) {
            return ApiResponse.onSuccess(paymentQueryService.getPaymentList(page, LocalDate.now(ZoneId.of("Asia/Seoul")).getYear(), LocalDate.now(ZoneId.of("Asia/Seoul")).getMonthValue()));
        } else if (year != null && month != null) {
            return ApiResponse.onSuccess(paymentQueryService.getPaymentList(page, year, month));
        } else throw new ApiException(ErrorStatus._BAD_REQUEST);
    }
}
