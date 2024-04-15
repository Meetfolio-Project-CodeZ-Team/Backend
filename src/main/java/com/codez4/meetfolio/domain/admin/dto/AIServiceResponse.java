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
        private AIServiceInfo AIServiceInfo;
        private List<AIModelInfo> modelEvaluation;
    }

    @Schema(description = "관리자 - AI 서비스 사용량 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AIServiceInfo {
        private int feedbackCount;
        private int analysisCount;
        private int totalCount;
        private double satisfaction;
    }

    @Schema(description = "관리자 - AI 서비스 통계 모델 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AIModelInfo {
        private String modelName;
        private double accuracy;
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
