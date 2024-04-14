package com.codez4.meetfolio.domain.member.dto;

import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Major;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.EnumValid;
import com.codez4.meetfolio.global.security.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.codez4.meetfolio.global.security.Password.ENCODER;

public class MemberRequest {

    @Schema(description = "회원 가입 요청 dto")
    @Getter
    public static class SignUpRequest {
        @NotBlank(message = "이메일 입력은 필수입니다.")
        private String email;

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        private String password;


        @Schema(description = "학년 및 학적, FIRST_GRADE/SECOND_GRADE/THIRD_GRADE/FOURTH_GRADE/GRADUATE")
        @NotBlank(message = "학년 및 학적 입력은 필수입니다.")
        @EnumValid(enumClass = Grade.class)
        private String grade;

        @NotBlank(message = "희망 직무 입력은 필수입니다. BACKEND/WEB/APP/DESIGN/AI")
        @EnumValid(enumClass = JobKeyword.class)
        private String jobKeyword;

        @NotBlank(message = "전공 입력은 필수입니다.")
        @EnumValid(enumClass = Major.class)
        private String major;
    }

    /*
        회원 생성 dto
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Post {
        private String email;
        private String password;
        private Grade grade;
        private JobKeyword jobKeyword;
        private Major major;
    }

    /*
        회원 수정 dto
     */
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Patch {
        private String grade;
        private String jobKeyword;
        private String major;
    }

    public static Member toEntity(Post post) {
        return Member.builder()
                .email(post.getEmail())
                .password(Password.encrypt(post.getPassword(), ENCODER))
                .grade(post.getGrade())
                .jobKeyword(post.getJobKeyword())
                .major(post.getMajor())
                .build();
    }
}
