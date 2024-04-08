package com.codez4.meetfolio.domain.member.dto;

import com.codez4.meetfolio.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static MemberInfo toMemberInfo(Member member) {

        return member == null ? null :
            MemberInfo.builder()
                .memberName(member.getEmail())
                .profile(member.getProfile())
                .major(member.getMajor().name())
                .build();
    }
}
