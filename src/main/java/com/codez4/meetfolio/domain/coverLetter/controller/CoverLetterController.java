package com.codez4.meetfolio.domain.coverLetter.controller;

import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import com.codez4.meetfolio.domain.analysis.service.AnalysisQueryService;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterRequest;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterInfo;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterProc;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterResult;
import com.codez4.meetfolio.domain.coverLetter.service.CoverLetterCommandService;
import com.codez4.meetfolio.domain.coverLetter.service.CoverLetterQueryService;
import com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;
import com.codez4.meetfolio.domain.feedback.service.FeedbackQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.AuthenticationMember;
import com.codez4.meetfolio.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "자기소개서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coverLetters")
public class CoverLetterController {

    private final CoverLetterQueryService coverLetterQueryService;
    private final CoverLetterCommandService coverLetterCommandService;
    private final FeedbackQueryService feedbackQueryService;
    private final AnalysisQueryService analysisQueryService;


    @Operation(summary = "자기소개서 상세정보 조회", description = "특정 자기소개서 정보를 조회합니다.")
    @Parameter(name = "coverLetterId", description = "자기소개서 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @GetMapping("/{coverLetterId}")
    public ApiResponse<CoverLetterResult> getCoverLetter(
        @PathVariable(name = "coverLetterId") Long coverLetterId) {

        CoverLetterInfo coverLetterInfo = coverLetterQueryService.getCoverLetterInfo(coverLetterId);
        FeedbackInfo feedbackInfo = feedbackQueryService.getFeedbackInfo(coverLetterId);
        AnalysisInfo analysisInfo = analysisQueryService.getAnalysisInfo(coverLetterId);
        CoverLetterResult coverLetterResult = CoverLetterResponse.toCoverLetterResult(
            coverLetterInfo, feedbackInfo, analysisInfo);

        return ApiResponse.onSuccess(coverLetterResult);
    }

    @Operation(summary = "자기소개서 작성 요청", description = "로그인 사용자는 자기소개서를 작성합니다.")
    @PostMapping
    public ApiResponse<CoverLetterProc> createCoverLetter(@AuthenticationMember Member member,
        @Valid @RequestBody CoverLetterRequest request) {

        return ApiResponse.onSuccess(coverLetterCommandService.write(member, request));
    }
}
