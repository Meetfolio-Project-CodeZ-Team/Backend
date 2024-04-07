package com.codez4.meetfolio.domain.feedback.dto;

import com.codez4.meetfolio.domain.feedback.Feedback;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedbackResponse {

    @Schema(description = "AI 자기소개서 피드백 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class FeedbackInfo {

        @Schema(description = "맞춤법 검사 결과")
        private String spellCheck;

        @Schema(description = "AI 피드백 결과")
        private String correction;

        @Schema(description = "추천 자기소개서 항목 1")
        private String recommendQuestion1;

        @Schema(description = "추천 자기소개서 항목 2")
        private String recommendQuestion2;

        @Schema(description = "추천 자기소개서 항목 3")
        private String recommendQuestion3;

    }

    public static FeedbackInfo toFeedbackInfo(Feedback feedback) {

        return FeedbackInfo.builder()
            .spellCheck(feedback.getSpellCheck())
            .correction(feedback.getCorrection())
            .recommendQuestion1(feedback.getRecommendQuestion1())
            .recommendQuestion2(feedback.getRecommendQuestion2())
            .recommendQuestion3(feedback.getRecommendQuestion3())
            .build();
    }
}
