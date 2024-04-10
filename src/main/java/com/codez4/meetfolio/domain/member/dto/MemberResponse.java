package com.codez4.meetfolio.domain.member.dto;

import com.codez4.meetfolio.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Schema(description = "회원 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberListResult {
        @Schema(description = "로그인 사용자 정보")
        private MemberInfo memberInfo;
        @Schema(description = "회원 목록")
        private MemberList memberList;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MemberList {

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
        private String major;
        @Schema(description = "희망 직무")
        private String jobKeyword;
        @Schema(description = "포인트")
        private int point;
    }


    public static MemberInfo toMemberInfo(Member member) {
        return member == null ? null :
                MemberInfo.builder()
                        .memberName(member.getEmail())
                        .profile(member.getProfile())
                        .major(member.getMajor().name())
                        .build();
    }

    public static MemberListResult toMemberListResult(Member member, Page<Member> members){
        return MemberListResult.builder()
                .memberInfo(toMemberInfo(member))
                .memberList(toMemberList(members))
                .build();
    }
    public static MemberList toMemberList(Page<Member> members){
        List<MemberDetailInfo> memberList = members.stream()
                .map(MemberResponse::toMemberDetailInfo)
                .toList();
        return MemberList.builder()
                .memberList(memberList)
                .listSize(memberList.size())
                .totalPage(members.getTotalPages())
                .totalElements(members.getTotalElements())
                .isFirst(members.isFirst())
                .isLast(members.isLast())
                .build();

    }

    public static MemberDetailInfo toMemberDetailInfo(Member member) {

        return member == null ? null :
                MemberDetailInfo.builder()
                        .memberId(member.getId())
                        .registrationDate(member.getCreatedAt().toLocalDate())
                        .email(member.getEmail())
                        .grade(member.getGrade().getDescription())
                        .major(member.getMajor().getDescription())
                        .jobKeyword(member.getJobKeyword().getDescription())
                        .point(member.getPoint())
                        .build();
    }

    public static MemberProc toMemberProc(Member member) {
        return MemberProc.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
