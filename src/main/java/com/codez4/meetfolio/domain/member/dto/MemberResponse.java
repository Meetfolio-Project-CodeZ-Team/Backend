package com.codez4.meetfolio.domain.member.dto;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class MemberResponse {

    @Schema(description = "로그인 사용자 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberInfo {

        @Schema(description = "권한, MEMBER/ADMIN")
        private String authority;
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

    @Schema(description = "회원 관리 - 회원 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberListResult {

        @Schema(description = "회원 목록")
        private List<MemberResponse.MemberDetailInfo> memberList;

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

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberDetailInfo {

        @Schema(description = "사용자 고유 번호")
        private Long memberId;
        @Schema(description = "회원 가입일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate registrationDate;
        @Schema(description = "사용자 이메일")
        private String email;
        @Schema(description = "사용자 학년 및 학적")
        private String grade;
        @Schema(description = "사용자 학과")
        @Enumerated(EnumType.STRING)
        private String major;
        @Schema(description = "희망 직무")
        private String jobKeyword;
        @Schema(description = "프로필 이모지")
        private String profile;
        @Schema(description = "포인트")
        private int point;
        @Schema(description = "활성 상태, ACTIVE/INACTIVE/WITHDRAW(탈퇴)")
        private String status;
    }


    public static MemberInfo toMemberInfo(Member member) {
        if (member != null) {
            if (member.getAuthority() == Authority.MEMBER) {
                return MemberInfo.builder()
                        .authority(member.getAuthority().name())
                        .memberName(member.getEmail().split("@")[0])
                        .profile(member.getProfile().name())
                        .major(member.getMajor())
                        .build();
            } else if (member.getAuthority() == Authority.ADMIN) {
                return MemberInfo.builder()
                        .authority(member.getAuthority().name())
                        .memberName(member.getEmail())
                        .profile(null)
                        .major(null)
                        .build();
            } else return null;
        } else return null;
    }

    public static MemberListResult toMemberList(Page<Member> members) {
        List<MemberDetailInfo> memberList = members.stream()
                .map(MemberResponse::toMemberDetailInfo)
                .toList();
        return MemberListResult.builder()
                .memberList(memberList)
                .listSize(memberList.size())
                .totalPage(members.getTotalPages())
                .totalElements(members.getTotalElements())
                .isFirst(members.isFirst())
                .isLast(members.isLast())
                .build();

    }

    public static MemberDetailInfo toMemberDetailInfo(Member member) {
        return MemberDetailInfo.builder()
                .memberId(member.getId())
                .registrationDate(member.getCreatedAt().toLocalDate())
                .email(member.getEmail())
                .point(member.getPoint())
                .grade(member.getGrade().getDescription())
                .major(member.getMajor())
                .jobKeyword(member.getJobKeyword().getDescription())
                .profile(member.getProfile().name())
                .point(member.getPoint())
                .status(member.getStatus().name())
                .build();
    }

    public static MemberProc toMemberProc(Member member) {
        return MemberProc.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
