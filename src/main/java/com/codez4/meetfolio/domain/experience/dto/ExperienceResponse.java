package com.codez4.meetfolio.domain.experience.dto;

import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ExperienceResponse {

    @Schema(description = "사용자 정보 및 경험 분해 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceResult {

        private MemberInfo memberInfo;
        private ExperienceInfo experienceInfo;
    }

    public static ExperienceResult toExperienceResult(MemberInfo memberInfo,
        ExperienceInfo experienceInfo) {
        return ExperienceResult.builder()
            .memberInfo(memberInfo)
            .experienceInfo(experienceInfo)
            .build();
    }

    @Schema(description = "경험 분해 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceInfo {

        @Schema(description = "경험 제목")
        private String title;

        @Schema(description = "경험 시작 기간 ")
        private LocalDate startDate;

        @Schema(description = "경험 종료 기간")
        private LocalDate endDate;

        @Schema(description = "경험 카테고리")
        private String experienceType;

        @Schema(description = "경험 업무 내용")
        private String task;

        @Schema(description = "경험 동기 & 이유")
        private String motivation;

        @Schema(description = "맡았던 직무")
        private String jobKeyword;

        @Schema(description = "사용한 기술 스택", example = "spring/mysql")
        private String stack;

        @Schema(description = "경험 세부 내용")
        private String detail;

        @Schema(description = "결과 및 성과")
        private String advance;
    }

    public static ExperienceInfo toExperienceInfo(Experience experience) {

        return ExperienceInfo.builder()
            .title(experience.getTitle())
            .startDate(experience.getStartDate())
            .endDate(experience.getEndDate())
            .experienceType(experience.getExperienceType())
            .task(experience.getTask())
            .motivation(experience.getMotivation())
            .jobKeyword(experience.getJobKeyword().getDescription())
            .stack(experience.getStack())
            .detail(experience.getDetail())
            .advance(experience.getAdvance())
            .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceProc {

        private Long experienceId;
        private LocalDateTime createdAt;
    }

    public static ExperienceProc toExperienceProc(Long experienceId) {
        return ExperienceProc.builder()
            .experienceId(experienceId)
            .createdAt(LocalDateTime.now())
            .build();
    }
}
