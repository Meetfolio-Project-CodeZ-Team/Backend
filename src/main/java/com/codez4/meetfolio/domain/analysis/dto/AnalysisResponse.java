package com.codez4.meetfolio.domain.analysis.dto;

import com.codez4.meetfolio.domain.analysis.Analysis;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AnalysisResponse {

    @Schema(description = "AI 직무 역량 분석 결과 DTO")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AnalysisInfo {

        @Schema(description = "직무 적합성 분석 결과")
        private Double jobSuitability;

        @Schema(description = "나의 역량 키워드 1")
        private String keyword1;

        @Schema(description = "나의 역량 키워드 2")
        private String keyword2;

        @Schema(description = "나의 역량 키워드 3")
        private String keyword3;
    }

    public static AnalysisInfo toAnalysisInfo(Analysis analysis) {

        return AnalysisInfo.builder()
            .jobSuitability(analysis.getJobSuitability())
            .keyword1(analysis.getKeyword1())
            .keyword2(analysis.getKeyword2())
            .keyword3(analysis.getKeyword3())
            .build();
    }
}
