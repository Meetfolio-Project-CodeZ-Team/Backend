package com.codez4.meetfolio.domain.coverLetter.controller;

import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import com.codez4.meetfolio.domain.analysis.service.AnalysisCommandService;
import com.codez4.meetfolio.domain.analysis.service.AnalysisQueryService;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterRequest;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterInfo;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterProc;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterResult;
import com.codez4.meetfolio.domain.coverLetter.service.CoverLetterCommandService;
import com.codez4.meetfolio.domain.coverLetter.service.CoverLetterQueryService;
import com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;
import com.codez4.meetfolio.domain.feedback.service.FeedbackCommandService;
import com.codez4.meetfolio.domain.feedback.service.FeedbackQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.domain.member.service.MemberQueryService;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.ApiResponse;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.toCoverLetterListResult;
import static com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.toOtherMemberCoverLetterListResult;

@Tag(name = "자기소개서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coverLetters")
public class CoverLetterController {

    private final CoverLetterQueryService coverLetterQueryService;
    private final CoverLetterCommandService coverLetterCommandService;
    private final FeedbackQueryService feedbackQueryService;
    private final FeedbackCommandService feedbackCommandService;
    private final AnalysisQueryService analysisQueryService;
    private final AnalysisCommandService analysisCommandService;
    private final MemberQueryService memberQueryService;


    @Operation(summary = "자기소개서 상세정보 조회", description = "특정 자기소개서 정보를 조회합니다.")
    @Parameter(name = "coverLetterId", description = "자기소개서 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @GetMapping("/{coverLetterId}")
    public ApiResponse<CoverLetterResult> getCoverLetter(@AuthenticationMember Member member,
                                                         @PathVariable(name = "coverLetterId") Long coverLetterId) {

        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        CoverLetterInfo coverLetterInfo = coverLetterQueryService.getCoverLetterInfo(member, coverLetterId);
        FeedbackInfo feedbackInfo = feedbackQueryService.getFeedbackInfo(coverLetterId);
        AnalysisInfo analysisInfo = analysisQueryService.getAnalysisInfo(coverLetterId);
        CoverLetterResult coverLetterResult = CoverLetterResponse.toCoverLetterResult(memberInfo,
                coverLetterInfo, feedbackInfo, analysisInfo);

        return ApiResponse.onSuccess(coverLetterResult);
    }

    @Operation(summary = "자기소개서 작성 요청", description = "로그인 사용자는 자기소개서를 작성합니다.")
    @PostMapping
    public ApiResponse<CoverLetterProc> createCoverLetter(@AuthenticationMember Member member,
                                                          @Valid @RequestBody CoverLetterRequest.Post request) {

        return ApiResponse.onSuccess(coverLetterCommandService.write(member, request));
    }

    @Operation(summary = "자기소개서 수정 요청", description = "특정 자기소개서 정보를 수정합니다.")
    @Parameter(name = "coverLetterId", description = "자기소개서 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PatchMapping("/{coverLetterId}")
    public ApiResponse<CoverLetterProc> updateCoverLetter(
            @AuthenticationMember Member member,
            @PathVariable(name = "coverLetterId") Long coverLetterId,
            @Valid @RequestBody CoverLetterRequest.Patch request) {

        return ApiResponse.onSuccess(coverLetterCommandService.update(coverLetterId, request));
    }

    @Operation(summary = "자기소개서 삭제 요청", description = "특정 자기소개서 정보를 삭제합니다.")
    @Parameter(name = "coverLetterId", description = "자기소개서 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @DeleteMapping("/{coverLetterId}")
    public ApiResponse<CoverLetterProc> deleteCoverLetter(
            @AuthenticationMember Member member,
            @PathVariable(name = "coverLetterId") Long coverLetterId) {

        return ApiResponse.onSuccess(coverLetterCommandService.softDelete(coverLetterId));
    }

    @Operation(summary = "내 자기소개서 목록 조회", description = "내 자기소개서 목록 정보를 조회합니다.")
    @GetMapping
    public ApiResponse<CoverLetterResponse.CoverLetterListResult> getMyCoverLetters(
            @AuthenticationMember Member member,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        return ApiResponse.onSuccess(toCoverLetterListResult(memberInfo,coverLetterQueryService.getMyCoverLetters(member, page)));
    }

    @Operation(summary = "다른 사용자의 자기소개서 목록 조회", description = "타 사용자의 자기소개서 목록 정보를 조회합니다.")
    @Parameter(name = "page", description = "페이징 번호, page, Query String입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/members")
    public ApiResponse<CoverLetterResponse.OtherMemberCoverLetterListResult> getOtherCoverLetters(
            @AuthenticationMember Member member,
            @RequestParam String memberName,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        MemberInfo memberInfo = MemberResponse.toMemberInfo(member);
        Member other = memberQueryService.findByMemberName(memberName);
        return ApiResponse.onSuccess(toOtherMemberCoverLetterListResult(memberInfo, coverLetterQueryService.getOtherCoverLetters(other, page), other));
    }

    @Operation(summary = "만족도 저장", description = "쿼리 스트링으로 피드백 ID 또는 분석 ID를, request body로 만족도를 전송합니다.")
    @PatchMapping("/satisfaction")
    @Parameter(name = "feedbackId", description = "피드백 ID, page, Query String입니다.", in = ParameterIn.QUERY)
    @Parameter(name = "analysisId", description = "분석 ID, page, Query String입니다.", in = ParameterIn.QUERY)
    public ApiResponse<String> saveSatisfaction(@AuthenticationMember Member member,
                                 @RequestParam(required = false) Long feedbackId,
                                 @RequestParam(required = false) Long analysisId,
                                 @Valid @RequestBody CoverLetterRequest.SatisfactionRequest request){
        if(feedbackId != null){
            feedbackCommandService.saveSatisfaction(feedbackQueryService.findById(feedbackId), request.getSatisfaction());
            return ApiResponse.onSuccess("만족도 저장이 완료되었습니다.");
        } else if (analysisId != null) {
            analysisCommandService.saveSatisfaction(analysisQueryService.findById(analysisId), request.getSatisfaction());
            return ApiResponse.onSuccess("만족도 저장이 완료되었습니다.");
        }
        else throw new ApiException(ErrorStatus._BAD_REQUEST);
    }
}
