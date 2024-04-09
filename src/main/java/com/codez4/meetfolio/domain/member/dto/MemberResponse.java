package com.codez4.meetfolio.domain.member.dto;

import com.codez4.meetfolio.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberResponse {

    @Schema(description = "사용자 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberInfo {

        @Schema(description = "사용자 이름")
        private String memberName;
        @Schema(description = "사용자 프로필")
        private String profile;
        @Schema(description = "사용자 학과")
        private String major;
    }

    @Schema(description = "회원 가입 완료 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberProc {

        @Schema(description = "회원 Id")
        private Long memberId;

        @Schema(description = "응답 DTO 생성 시간")
        private LocalDateTime createdAt;
    }

    public static MemberInfo toMemberInfo(Member member) {

        return member == null ? null :
            MemberInfo.builder()
                .memberName(member.getEmail())
                .profile(member.getProfile())
                .major(member.getMajor().name())
                .build();
    }

    public static MemberProc toMemberProc(Member member) {
        return MemberProc.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
