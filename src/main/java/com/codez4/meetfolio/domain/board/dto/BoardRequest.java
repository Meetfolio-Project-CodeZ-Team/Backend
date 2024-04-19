package com.codez4.meetfolio.domain.board.dto;


import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.annotation.EnumValid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class BoardRequest {
    @Schema(description = "취업 정보 게시물 작성 요청 dto")
    @Getter
    public static class EmploymentBoardRequest {
        @Schema(description = "제목")
        private String title;
        @Schema(description = "내용")
        private String content;
        @Schema(description = "직무 카테고리, BACKEND/WEB/APP/AI/DESIGN/ALL")
        @EnumValid(enumClass = JobKeyword.class)
        private String jobKeyword;

    }

    @Schema(description = "그룹원 모집 게시물 작성 요청 dto")
    @Getter
    public static class GroupBoardRequest {
        @Schema(description = "제목")
        private String title;

        @Schema(description = "내용")
        private String content;

        @Schema(description = "그룹원 모집 카테고리, STUDY/CONTEST", example = "STUDY")
        @EnumValid(enumClass = GroupCategory.class)
        private String groupCategory;

        @Schema(description = "원하는 스택, '/'로 구분지어서 입력", example = "백엔드/디자인/PM")
        private String recruitment;

        @Schema(description = "인원 수", example = "5")
        private Integer peopleNumber;

    }

    @Schema(description = "게시물 수정 요청 dto")
    @Getter
    public static class BoardUpdate {
        @Schema(description = "제목")
        private String title;

        @Schema(description = "내용")
        private String content;

        @Schema(description = "그룹원 모집 카테고리, STUDY/CONTEST", example = "STUDY")
        private String groupCategory;

        @Schema(description = "원하는 스택, '/'로 구분지어서 입력", example = "백엔드/디자인/PM")
        private String recruitment;

        @Schema(description = "인원 수", example = "5")
        private Integer peopleNumber;

        @Schema(description = "직무 카테고리, BACKEND/WEB/APP/AI/DESIGN/ALL")
        private String jobKeyword;
    }

    @Getter
    @Builder
    public static class EmploymentBoardPatch{
        private String title;

        private String content;

        private JobKeyword jobKeyword;
    }

    @Getter
    @Builder
    public static class GroupBoardPatch{
        private String title;

        private String content;

        private GroupCategory groupCategory;

        private String recruitment;

        private Integer peopleNumber;
    }

    public static EmploymentBoard toEmploymentBoard(EmploymentBoardRequest request, Member member) {
        return EmploymentBoard.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .jobKeyword(JobKeyword.convert(request.getJobKeyword()))
                .member(member)
                .build();
    }

    public static GroupBoard toGroupBoard(GroupBoardRequest request, Member member) {
        return GroupBoard.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .groupCategory(GroupCategory.convert(request.getGroupCategory()))
                .recruitment(request.getRecruitment())
                .peopleNumber(request.getPeopleNumber())
                .member(member)
                .build();
    }

    public static EmploymentBoardPatch toEmploymentBoardPatch(BoardUpdate boardUpdate){
        return EmploymentBoardPatch.builder()
                .title(boardUpdate.getTitle())
                .content(boardUpdate.getContent())
                .jobKeyword(JobKeyword.convert(boardUpdate.getJobKeyword()))
                .build();
    }

    public static GroupBoardPatch toGroupBoardPatch(BoardUpdate boardUpdate){
        return GroupBoardPatch.builder()
                .title(boardUpdate.getTitle())
                .content(boardUpdate.getContent())
                .groupCategory(GroupCategory.convert(boardUpdate.getGroupCategory()))
                .peopleNumber(boardUpdate.getPeopleNumber())
                .recruitment(boardUpdate.getRecruitment())
                .build();
    }
}
