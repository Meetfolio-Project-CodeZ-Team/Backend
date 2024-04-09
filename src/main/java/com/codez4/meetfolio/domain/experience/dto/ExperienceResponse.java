package com.codez4.meetfolio.domain.experience.dto;

import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

        @Schema(description = "경험 시작 기간")
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

    @Schema(description = "경험 분해 작성 & 수정 & 삭제 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceProc {

        @Schema(description = "경험 분해 Id")
        private Long experienceId;

        @Schema(description = "응답 DTO 생성 시간")
        private LocalDateTime createdAt;
    }

    public static ExperienceProc toExperienceProc(Long experienceId) {
        return ExperienceProc.builder()
            .experienceId(experienceId)
            .createdAt(LocalDateTime.now())
            .build();
    }

    @Schema(description = "나의 경험 카드 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceCardResult {

        private MemberInfo memberInfo;
        private ExperienceCardInfo experienceCardInfo;
    }

    public static ExperienceCardResult toExperienceCardResult(MemberInfo memberInfo,
        ExperienceCardInfo experienceCardInfo) {

        return ExperienceCardResult.builder()
            .memberInfo(memberInfo)
            .experienceCardInfo(experienceCardInfo)
            .build();
    }

    @Schema(description = "경험 카드 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceCardInfo {

        @Schema(description = "경험 카드 목록")
        private List<ExperienceCardItem> experienceCardItems;

        @Schema(description = "페이징된 리스트의 항목 개수")
        private Integer listSize;

        @Schema(description = "총 페이징 수 ")
        private Integer totalPage;

        @Schema(description = "전체 데이터의 개수")
        private Long totalElements;

        @Schema(description = "첫 페이지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지의 여부")
        private Boolean isLast;
    }

    public static ExperienceCardInfo toExperienceCardInfo(Page<Experience> experiences) {

        List<ExperienceCardItem> experienceCardItems = experiences.stream()
            .map(ExperienceResponse::toExperienceCardItem)
            .toList();

        return ExperienceCardInfo.builder()
            .experienceCardItems(experienceCardItems)
            .listSize(experienceCardItems.size())
            .totalPage(experiences.getTotalPages())
            .totalElements(experiences.getTotalElements())
            .isFirst(experiences.isFirst())
            .isLast(experiences.isLast())
            .build();
    }

    @Schema(description = "경험 카드 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExperienceCardItem {

        @Schema(description = "경험 제목")
        private String title;

        @Schema(description = "경험 시작 기간")
        private String startDate;

        @Schema(description = "경험 종료 기간")
        private String endDate;

        @Schema(description = "경험 카테고리")
        private String experienceType;

        @Schema(description = "맡았던 직무")
        private String jobKeyword;

        @Schema(description = "사용한 기술 스택")
        private String stack;
    }

    public static ExperienceCardItem toExperienceCardItem(Experience experience) {

        return ExperienceCardItem.builder()
            .title(experience.getTitle())
            .startDate(experience.getStartDate().toString())
            .endDate(experience.getEndDate().toString())
            .experienceType(experience.getExperienceType())
            .jobKeyword(experience.getJobKeyword().getDescription())
            .stack(experience.getStack())
            .build();
    }

    @Schema(description = "랜딩페이지 추천 경험 카드 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RecommendCard {

        private MemberInfo memberInfo;
        private List<ExperienceCardItem> recommendCardInfo;
    }

    public static RecommendCard toRecommendCard(MemberInfo memberInfo,
        List<ExperienceCardItem> experienceCardItems) {

        return RecommendCard.builder()
            .memberInfo(memberInfo)
            .recommendCardInfo(experienceCardItems)
            .build();
    }

    public static List<ExperienceCardItem> toExperienceCardItems(List<Experience> experiences) {
        return experiences.stream()
            .map(ExperienceResponse::toExperienceCardItem)
            .toList();
    }
}
