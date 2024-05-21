package com.codez4.meetfolio.domain.board.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardResponse.BoardItem.BoardItemBuilder;
import com.codez4.meetfolio.domain.enums.BoardType;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
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

public class BoardResponse {

    @Schema(description = "게시글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardListResult {
        @Schema(description = "로그인 사용자 정보")
        private MemberResponse.MemberInfo memberInfo;
        @Schema(description = "게시물 목록 정보")
        private BoardList boardListInfo;
    }

    @Schema(description = "관리자 커뮤니티 게시글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardList {

        @Schema(description = "게시글 목록")
        private List<BoardItem> boardInfo;

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

    @Schema(description = "게시글 1개 조회 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardResult {
        @Schema(description = "로그인 사용자 정보")
        private MemberResponse.MemberInfo memberInfo;
        @Schema(description = "게시물 1개 정보")
        private BoardItem boardInfo;
    }

    public static BoardList toBoardList( Page<BoardQueryItem> boards){
        List<BoardItem> boardList = boards.stream().map(BoardResponse::toBoardItem).toList();
        return BoardList.builder()
                .boardInfo(boardList)
                .listSize(boardList.size())
                .isFirst(boards.isFirst())
                .isLast(boards.isLast())
                .totalElements(boards.getTotalElements())
                .totalPage(boards.getTotalPages())
                .build();
    }

    public static BoardListResult toBoardListResult(MemberInfo memberInfo, Page<BoardQueryItem> boards) {
        return BoardListResult.builder()
                .memberInfo(memberInfo)
                .boardListInfo(toBoardList(boards))
                .build();
    }

    public static BoardResult toBoardResult(MemberInfo memberInfo, BoardItem boardItem) {

        return BoardResult.builder()
                .memberInfo(memberInfo)
                .boardInfo(boardItem)
                .build();
    }

    @Schema(description = "커뮤니티 게시글 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardItem {

        @Schema(description = "게시글 아이디")
        private Long boardId;

        @Schema(description = "게시글 유형, EMPLOYMENT/GROUP")
        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        @Schema(description = "게시글 작성자")
        private String memberName;

        @Schema(description = "작성자 프로필")
        private String profile;

        @Schema(description = "게시글 제목")
        private String title;

        @Schema(description = "게시글 내용")
        private String content;

        @Schema(description = "게시글의 좋아요 개수")
        private Integer likeCount;

        @Schema(description = "좋아요 상태 여부")
        private Status likeStatus;

        @Schema(description = "게시글의 댓글 개수")
        private Integer commentCount;

        @Schema(description = "취업 게시글의 카테고리")
        private String jobCategory;

        @Schema(description = "그룹원 모집 게시글의 카테고리")
        private String groupCategory;

        @Schema(description = "그룹원 모집의 모집 스택 기술")
        private String recruitment;

        @Schema(description = "그룹원 모집의 인원 수")
        private Integer peopleNumber;

        @Schema(description = "게시글 등록 날짜")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate registrationDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BoardItem boardItem = (BoardItem) o;
            return Objects.equals(getBoardId(), boardItem.getBoardId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getBoardId());
        }

    }

    @Schema(description = "게시물 작성/수정/삭제 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardProc {

        @Schema(description = "게시물 Id")
        private Long boardId;

        @Schema(description = "응답 DTO 생성 시간")
        private LocalDateTime createdAt;
    }

    public static BoardItem toBoardItem(BoardQueryItem board) {
        BoardItemBuilder boardItemBuilder = BoardItem.builder()
                .memberName(board.getEmail().split("@")[0])
                .profile(board.getProfile())
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(board.getLikeCount())
                .likeStatus(board.getStatus())
                .commentCount(board.getCommentCount())
                .registrationDate(board.getCreatedAt().toLocalDate());
        if (board.getJobKeyword() != null) {
            boardItemBuilder
                    .boardType(BoardType.EMPLOYMENT)
                    .jobCategory(board.getJobKeyword().getDescription());
        } else if (board.getGroupCategory() != null) {
            boardItemBuilder
                    .boardType(BoardType.GROUP)
                    .groupCategory(board.getGroupCategory().getDescription())
                    .recruitment(board.getRecruitment())
                    .peopleNumber(board.getPeopleNumber())
                    .build();
        }
        return boardItemBuilder.build();
    }

    public static BoardProc toBoardProc(Board board, LocalDateTime createdAt){
        return BoardProc.builder()
                .boardId(board.getId())
                .createdAt(createdAt)
                .build();
    }

}
