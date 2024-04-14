package com.codez4.meetfolio.domain.experience.dto;

import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "경험 분해 작성 및 수정 Request")
@Getter
public class ExperienceRequest {

    @Schema(description = "경험 분해 제목")
    private String title;

    @Schema(description = "경험 분해 시작 기간", example = "2024-02-02")
    private String startDate;

    @Schema(description = "경험 분해 종료 기간", example = "2024-03-01")
    private String endDate;

    @Schema(description = "경험 카테고리")
    private String experienceType;

    @Schema(description = "경험 업무 내용")
    private String task;

    @Schema(description = "경험 동기 & 이유")
    private String motivation;

    @Schema(description = "맡았던 직무, BACKEND/WEB/APP/DESIGN/AI", example = "BACKEND")
    @EnumValid(enumClass = JobKeyword.class)
    private String jobKeyword;

    @Schema(description = "사용한 기술 스택", example = "spring/mysql")
    private String stack;

    @Schema(description = "경험 세부 내용")
    private String detail;

    @Schema(description = "결과 및 성과")
    private String advance;

    public static Experience toEntity(ExperienceRequest reqeust, Member member) {

        return Experience.builder()
            .title(reqeust.getTitle())
            .startDate(LocalDate.parse(reqeust.getStartDate()))
            .endDate(LocalDate.parse(reqeust.getEndDate()))
            .experienceType(reqeust.getExperienceType())
            .task(reqeust.getTask())
            .motivation(reqeust.getMotivation())
            .jobKeyword(JobKeyword.valueOf(reqeust.getJobKeyword()))
            .stack(reqeust.getStack())
            .detail(reqeust.getDetail())
            .advance(reqeust.getAdvance())
            .member(member)
            .build();
    }
}
