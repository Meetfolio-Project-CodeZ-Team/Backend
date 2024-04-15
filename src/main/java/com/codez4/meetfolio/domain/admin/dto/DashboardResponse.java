package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.enums.JobKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

public class DashboardResponse {

    @Schema(description = "사용자 - 충전 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class DashboardResult {
        private AISolutionInfo aiSolutionInfo;
        private MemberInfo memberInfo;
        private PointInfo pointInfo;
        private int paymentInfo;
    }

    @Schema(description = "사용자 - 충전 내역 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AISolutionInfo {
        private int feedbackCount;
        private int analysisCount;
        private int totalCount;
        private double satisfaction;
    }

    @Schema(description = "대시보드 - 직무별 회원 수 통계 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberInfo {
        private int totalCount;
        private int backend;
        private int web;
        private int app;
        private int design;
        private int ai;
    }

    @Schema(description = "대시보드 - 포인트 사용량 통계 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PointInfo {
        private int totalPoint;
        private int solutionPoint;
        private int analysisPoint;
    }

    public static DashboardResult toDashboardResult(AISolutionInfo aiSolutionInfo, MemberInfo memberInfo, PointInfo pointInfo, int paymentInfo) {
        return DashboardResult.builder()
                .aiSolutionInfo(aiSolutionInfo)
                .memberInfo(memberInfo)
                .pointInfo(pointInfo)
                .paymentInfo(paymentInfo)
                .build();
    }

    public static AISolutionInfo toAISolutionInfo(int feedbackCount, int analysisCount, double satisfaction) {
        return AISolutionInfo.builder()
                .feedbackCount(feedbackCount)
                .analysisCount(analysisCount)
                .totalCount(feedbackCount + analysisCount)
                .satisfaction(satisfaction)
                .build();
    }

    public static MemberInfo toMemberInfo(int totalCount, Map<JobKeyword, Integer> jobCount) {
        return MemberInfo.builder()
                .totalCount(totalCount)
                .backend(jobCount.get(JobKeyword.BACKEND))
                .web(jobCount.get(JobKeyword.WEB))
                .app(jobCount.get(JobKeyword.APP))
                .design(jobCount.get(JobKeyword.DESIGN))
                .ai(jobCount.get(JobKeyword.AI))
                .build();
    }

    public static PointInfo toPointInfo(int totalPoint, int solutionPoint, int analysisPoint) {
        return PointInfo.builder()
                .totalPoint(totalPoint)
                .solutionPoint(solutionPoint)
                .analysisPoint(analysisPoint)
                .build();
    }
}
