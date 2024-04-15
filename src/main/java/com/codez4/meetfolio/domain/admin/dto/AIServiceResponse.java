package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.model.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AIServiceResponse {
    @Schema(description = "관리자 - AI 서비스 통계 조회 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AIServiceResult {
        @Schema(description = "AI 서비스 사용량 통계")
        private AIServiceInfo AIServiceInfo;
        @Schema(description = "AI 모델 성능 지표")
        private List<AIModelInfo> modelEvaluation;
    }

    @Schema(description = "관리자 - AI 서비스 사용량 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AIServiceInfo {
        @Schema(description = "AI 피드백 서비스 사용량")
        private int feedbackCount;
        @Schema(description = "AI 직무 역량 분석 서비스 사용량")
        private int analysisCount;
        @Schema(description = "총 AI 서비스 사용량")
        private int totalCount;
        @Schema(description = "AI 서비스 만족도")
        private double satisfaction;
    }

    @Schema(description = "관리자 - AI 서비스 통계 모델 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AIModelInfo {
        @Schema(description = "모델명")
        private String modelName;
        @Schema(description = "정확도")
        private double accuracy;
        @Schema(description = "손실값")
        private double loss;
    }


    public static AIServiceResult toAIServiceResult(AIServiceInfo AIServiceInfo, List<AIModelInfo> models) {
        return AIServiceResult.builder()
                .AIServiceInfo(AIServiceInfo)
                .modelEvaluation(models)
                .build();
    }

    public static AIServiceResponse.AIServiceInfo toAIServiceInfo(int feedbackCount, int analysisCount, double satisfaction) {
        return AIServiceResponse.AIServiceInfo.builder()
                .feedbackCount(feedbackCount)
                .analysisCount(analysisCount)
                .totalCount(feedbackCount + analysisCount)
                .satisfaction(satisfaction)
                .build();
    }

    public static AIModelInfo toAIModelInfo(Model model) {
        return AIModelInfo.builder()
                .modelName(model.getName())
                .accuracy(model.getAccuracy())
                .loss(model.getLoss())
                .build();
    }
}
