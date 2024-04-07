package com.codez4.meetfolio.domain.coverLetter.dto;

import static com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import static com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CoverLetterResponse {

    @Schema(description = "자기소개서 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterInfo {

        @Schema(description = "자기소개서 문항")
        private String question;

        @Schema(description = "자기소개서 문항 답변")
        private String answer;

        @Schema(description = "자기소개서 공유 여부")
        private String shareType;

        @Schema(description = "강조하고 싶은 키워드 1")
        private String keyword1;

        @Schema(description = "강조하고 싶은 키워드 2")
        private String keyword2;

        @Schema(description = "지원 직무")
        private String jobKeyword;
    }

    public static CoverLetterInfo toCoverLetterInfo(CoverLetter coverLetter) {

        return CoverLetterInfo.builder()
            .question(coverLetter.getQuestion())
            .answer(coverLetter.getAnswer())
            .shareType(coverLetter.getShareType().getDescription())
            .keyword1(coverLetter.getKeyword1())
            .keyword2(coverLetter.getKeyword2())
            .jobKeyword(coverLetter.getJobKeyword().getDescription())
            .build();

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterResult {

        private CoverLetterInfo coverLetterInfo;
        private FeedbackInfo feedbackInfo;
        private AnalysisInfo analysisInfo;
    }

    public static CoverLetterResult toCoverLetterResult(CoverLetterInfo coverLetterInfo,
        FeedbackInfo feedbackInfo, AnalysisInfo analysisInfo) {

        return CoverLetterResult.builder()
            .coverLetterInfo(coverLetterInfo)
            .feedbackInfo(feedbackInfo)
            .analysisInfo(analysisInfo)
            .build();
    }
}
