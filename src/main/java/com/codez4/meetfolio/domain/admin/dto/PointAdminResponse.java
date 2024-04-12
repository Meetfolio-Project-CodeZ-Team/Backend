package com.codez4.meetfolio.domain.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PointAdminResponse {
    @Schema(description = "포인트 사용 통계 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PointStatics {

        @Schema(description = "요청 년/월")
        private String yearMonth;

        @Schema(description = "자소서 조회 포인트 샤용량")
        private int coverLetterPoint;

        @Schema(description = "AI 직무 분석 포인트 사용량")
        private int analysisPoint;

        @Schema(description = "총 포인트 사용량")
        private int totalPoint;
    }

    public static PointStatics toPointStatics(String yearMonth, int coverLetterPoint, int analysisPoint) {
        return PointStatics.builder()
                .yearMonth(yearMonth)
                .coverLetterPoint(coverLetterPoint)
                .analysisPoint(analysisPoint)
                .totalPoint(coverLetterPoint + analysisPoint)
                .build();
    }
}
