package com.codez4.meetfolio.domain.coverLetter.dto;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.ShareType;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

public class CoverLetterRequest {

    @Schema(name = "CoverLetterPost", description = "자기소개서 작성 DTO")
    @Getter
    public static class Post {

        @Schema(description = "자기소개서 문항")
        private String question;

        @Schema(description = "자기소개서 답변")
        private String answer;

        @Schema(description = "자기소개서 공개 여부, PUBLIC / PRIVATE", example = "PUBLIC")
        @EnumValid(enumClass = ShareType.class)
        private String shareType;
    }

    @Schema(name = "CoverLetterPatch", description = "자기소개서 수정 DTO")
    @Getter
    public static class Patch {

        @Schema(description = "자기소개서 문항")
        private String question;

        @Schema(description = "자기소개서 답변")
        private String answer;

        @Schema(description = "자기소개서 공개 여부, PUBLIC / PRIVATE", example = "PUBLIC")
        @EnumValid(enumClass = ShareType.class)
        private String shareType;

        @Schema(description = "나의 역량 키워드 1")
        private String keyword1;

        @Schema(description = "나의 역량 키워드 2")
        private String keyword2;

        @Schema(description = "자기소개서 지원 직무, BACKEND/WEB/APP/DESIGN/AI", example = "BACKEND")
        @EnumValid(enumClass = JobKeyword.class)
        private String jobKeyword;
    }

    @Schema( description = "만족도 DTO")
    @Getter
    public static class SatisfactionRequest{
        @Schema(description = "만족도, 0부터 5까지의 정수만 입력 가능합니다." )
        @Max(value = 5)
        @Min(value = 0)
        private int satisfaction;
    }

    public static CoverLetter toEntity(Member member, CoverLetterRequest.Post request) {

        return CoverLetter.builder()
            .question(request.getQuestion())
            .answer(request.getAnswer())
            .shareType(ShareType.convert(request.getShareType()))
            .member(member)
            .build();
    }

    @Getter
    public static class CoverLetterOther {

        @Schema(description = "다른 사용자의 이름 (이메일 정보는 백엔드에서 처리)")
        private String memberName;
    }
}
