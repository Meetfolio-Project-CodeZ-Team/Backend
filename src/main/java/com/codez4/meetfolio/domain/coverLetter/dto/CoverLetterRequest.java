package com.codez4.meetfolio.domain.coverLetter.dto;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.ShareType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "자기소개서 작성 & 수정 DTO")
@Getter
public class CoverLetterRequest {

    @Schema(description = "자기소개서 문항")
    private String question;

    @Schema(description = "자기소개서 답변")
    private String answer;

    @Schema(description = "자기소개서 공개 여부", example = "PUBLIC")
    @EnumValid(enumClass = ShareType.class)
    private String shareType;

    @Schema(description = "나의 역량 키워드 1")
    private String keyword1;

    @Schema(description = "나의 역량 키워드 2")
    private String keyword2;

    @Schema(description = "자기소개서 지원 직무", example = "BACKEND")
    @EnumValid(enumClass = JobKeyword.class)
    private String jobKeyword;

    public static CoverLetter toEntity(Member member, CoverLetterRequest request) {

        return CoverLetter.builder()
            .question(request.getQuestion())
            .answer(request.getAnswer())
            .shareType(ShareType.convert(request.getShareType()))
            .keyword1(request.getKeyword1())
            .keyword2(request.getKeyword2())
            .jobKeyword(JobKeyword.convert(request.getJobKeyword()))
            .member(member)
            .build();
    }
}
