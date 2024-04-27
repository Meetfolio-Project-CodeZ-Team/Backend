package com.codez4.meetfolio.domain.admin.controller;

import com.codez4.meetfolio.domain.admin.dto.*;
import com.codez4.meetfolio.domain.admin.service.AdminCommandService;
import com.codez4.meetfolio.domain.admin.service.AdminQueryService;
import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.service.BoardCommandService;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.utils.TimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;

import static com.codez4.meetfolio.domain.board.dto.BoardResponse.toBoardProc;

@Tag(name = "관리자 API")
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminQueryService adminQueryService;
    private final AdminCommandService adminCommandService;
    private final MemberQueryService memberQueryService;
    private final BoardQueryService boardQueryService;
    private final BoardCommandService boardCommandService;

    @Operation(summary = "대시 보드 조회", description = "관리자 메인페이지의 대시보드를 조회합니다.")
    @GetMapping("/dashboard")
    public ApiResponse<DashboardResponse.DashboardResult> getDashboard(@AuthenticationMember Member admin) {
        return ApiResponse.onSuccess(adminQueryService.getDashboard());
    }

    @Operation(summary = "회원 관리 - 회원 목록 조회", description = "회원 관리 메뉴의 회원 목록을 조회합니다.")
    @Parameter(name = "jobKeyword", description = "직무 키워드, Query String입니다. BACKEND/WEB/APP/DESIGN/AI", required = false, example = "BACKEND", in = ParameterIn.QUERY)
    @GetMapping("/members-management")
    public ApiResponse<MemberResponse.MemberListResult> getMemberList(@AuthenticationMember Member admin,
                                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "jobKeyword", required = false) String jobKeyword) {
        JobKeyword jobKeywordEnum;
        if (jobKeyword != null) {
            jobKeywordEnum = JobKeyword.convert(jobKeyword);
            if (jobKeywordEnum == null) throw new ApiException(ErrorStatus._BAD_REQUEST);
        } else jobKeywordEnum = null;
        return ApiResponse.onSuccess(adminQueryService.getMemberList(page, jobKeywordEnum));
    }

    @Operation(summary = "회원 관리 -회원 비활성화", description = "회원관리 메뉴에서 회원을 비활성화합니다.")
    @Parameter(name = "memberId", description = "회원 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @DeleteMapping("/members-management/{memberId}")
    public ApiResponse<String> inactivateMember(@AuthenticationMember Member admin,
                                                @PathVariable(value = "memberId") Long memberId) {
        Member member = memberQueryService.findById(memberId);
        adminCommandService.inactivateMember(member);
        return ApiResponse.onSuccess("회원 비활성화 성공입니다.");
    }

    @Operation(summary = "회원 관리 - 회원 검색", description = "회원 관리 메뉴에서 회원을 검색합니다.")
    @Parameter(name = "keyword", description = "검색어, Query String입니다.", required = true, example = "BACKEND", in = ParameterIn.QUERY)
    @GetMapping("/members-management/search")
    public ApiResponse<MemberResponse.MemberListResult> getMemberListByKeyword(@AuthenticationMember Member admin,
                                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "keyword") String keyword) {

        return ApiResponse.onSuccess(adminQueryService.getMemberListByKeyword(page, keyword));
    }


    @Operation(summary = "매출 관리 - 포인트 사용 통계", description = "매출 관리 메뉴에서 포인트 사용 통계를 조회합니다. year, month 모두 입력 안 할 시 현재 달의 통계를 전송합니다. year, month 중 하나만 입력 시 에러를 반환합니다.")
    @Parameter(name = "year", description = "년도", required = false, example = "2024", in = ParameterIn.QUERY)
    @Parameter(name = "month", description = "월", required = false, example = "4", in = ParameterIn.QUERY)
    @GetMapping("/point-management")
    public ApiResponse<PointResponse.PointStatics> getPointStatics(@AuthenticationMember Member admin,
                                                                   @RequestParam(required = false) Integer year,
                                                                   @RequestParam(required = false) Integer month) {
        if (year == null && month == null) {
            return ApiResponse.onSuccess(adminQueryService.getPointStatics(LocalDate.now(ZoneId.of("Asia/Seoul")).getYear(), LocalDate.now(ZoneId.of("Asia/Seoul")).getMonthValue()));
        } else if (year != null && month != null)
            return ApiResponse.onSuccess(adminQueryService.getPointStatics(year, month));
        else throw new ApiException(ErrorStatus._BAD_REQUEST);
    }

    @Operation(summary = "매출 관리 - 결제 내역 조회", description = "매출 관리 메뉴에서 결제 내역을 조회합니다. year, month 모두 입력 안 할 시 현재 달의 통계를 전송합니다. year, month 중 하나만 입력 시 에러를 반환합니다.")
    @Parameter(name = "year", description = "년도", required = false, example = "2024", in = ParameterIn.QUERY)
    @Parameter(name = "month", description = "월", required = false, example = "4", in = ParameterIn.QUERY)
    @GetMapping("/payment-management")
    public ApiResponse<PaymentResponse.PaymentResult> getPaymentList(@AuthenticationMember Member admin,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(required = false) Integer year,
                                                                     @RequestParam(required = false) Integer month) {
        if (year == null && month == null) {
            return ApiResponse.onSuccess(adminQueryService.getPaymentList(page, LocalDate.now(ZoneId.of("Asia/Seoul")).getYear(), LocalDate.now(ZoneId.of("Asia/Seoul")).getMonthValue()));
        } else if (year != null && month != null) {
            return ApiResponse.onSuccess(adminQueryService.getPaymentList(page, year, month));
        } else throw new ApiException(ErrorStatus._BAD_REQUEST);
    }

    @Operation(summary = "AI 관리 - AI 서비스 통계", description = "AI 관리 메뉴에서 AI 서비스 통계를 조회합니다.")
    @GetMapping("/model-management")
    public ApiResponse<AIServiceResponse.AIServiceResult> getAIServiceStatics(@AuthenticationMember Member admin) {
        return ApiResponse.onSuccess(adminQueryService.getAIServiceStatics());
    }

    @Operation(summary = "AI 관리 - 학습 데이터 리스트 조회", description = "AI 관리 메뉴에서 AI 학습 데이터 리스트를 조회합니다.")
    @GetMapping("/data-management")
    public ApiResponse<DatasetResponse.DatasetInfo> getAIServiceStatics(@AuthenticationMember Member admin,
                                                                        @RequestParam(value = "page", defaultValue = "0") int page) {
        return ApiResponse.onSuccess(adminQueryService.getDatasetList(page));
    }

    @Operation(summary = "AI 관리 - 학습 데이터 추가", description = "AI 관리 메뉴에서 AI 학습 데이터를 저장합니다.")
    @PostMapping("/data-management")
    public ApiResponse<DatasetResponse.DatasetProc> getAIServiceStatics(@AuthenticationMember Member admin,
                                                                        @Valid @RequestBody DatasetRequest request) {
        return ApiResponse.onSuccess(adminCommandService.saveDataset(request));
    }

    @Operation(summary = "커뮤니티 관리 - 게시물 목록 조회", description = "전체 게시물 조회 및 keyword를 입력하여 게시물 검색을 합니다.")
    @GetMapping("/board-management")
    @Parameter(name = "keyword", description = "검색어, Query String입니다.", in = ParameterIn.QUERY)
    public ApiResponse<com.codez4.meetfolio.domain.admin.dto.BoardResponse.BoardAdminResult> getBoards(@AuthenticationMember Member admin,
                                                                                                       @RequestParam(name = "keyword", required = false) String keyword,
                                                                                                       @RequestParam(value = "page", defaultValue = "0") int page) {
        PageRequest pageRequest = PageRequest.of(page, 12, Sort.by("createdAt").descending());
        com.codez4.meetfolio.domain.admin.dto.BoardResponse.BoardAdminResult boards;
        if (keyword != null) {
            boards = adminQueryService.getBoardsByKeyword(keyword, pageRequest);
        } else boards = adminQueryService.getBoards(pageRequest);
        return ApiResponse.onSuccess(boards);
    }

    @Operation(summary = "커뮤니티 관리 - 게시물 삭제")
    @Parameter(name = "boardId", description = "게시물 Id, Path Variable입니다.", in = ParameterIn.PATH)
    @DeleteMapping("/board-management/{boardId}")
    public ApiResponse<BoardResponse.BoardProc> deleteBoard(@AuthenticationMember Member admin,
                                                            @PathVariable Long boardId) {
        Board board = boardQueryService.findById(boardId);
        boardCommandService.deleteBoard(board);
        return ApiResponse.onSuccess(toBoardProc(board, TimeUtils.getCurrentTime()));
    }

}
