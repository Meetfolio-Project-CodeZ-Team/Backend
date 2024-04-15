package com.codez4.meetfolio.domain.board.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.board.dto.BoardResponse.BoardItem.BoardItemBuilder;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public class BoardResponse {

    @Schema(description = "내가 작성한 게시글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyBoardResult {

        private MemberResponse.MemberInfo memberInfo;
        private BoardInfo boardInfo;
    }

    public static MyBoardResult toMyBoardResult(MemberInfo memberInfo, BoardInfo boardInfo) {

        return MyBoardResult.builder()
            .memberInfo(memberInfo)
            .boardInfo(boardInfo)
            .build();
    }

    @Schema(description = "내가 작성한 게시글 페이징 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardInfo {

        @Schema(description = "내가 작성한 게시글 목록")
        private List<BoardItem> boardItems;

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

    public static <T> BoardInfo toBoardInfo(Page<T> page, List<Status> statusList) {
        List<BoardItem> boardItems;
        List<Board> boards;

        boards = page.getContent().stream()
            .map(item -> {
                if (item instanceof Board) {
                    return (Board) item;
                } else if (item instanceof Like) {
                    return ((Like) item).getBoard();
                } else {
                    throw new ApiException(ErrorStatus._BAD_REQUEST);
                }
            })
            .toList();

        boardItems = IntStream.range(0, boards.size())
            .mapToObj(i -> toBoardItem(boards.get(i), statusList.get(i)))
            .toList();

        return BoardInfo.builder()
            .boardItems(boardItems)
            .listSize(boardItems.size())
            .totalPage(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .isFirst(page.isFirst())
            .isLast(page.isLast())
            .build();
    }

    @Schema(description = "커뮤니티 게시글 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardItem {

        @Schema(description = "게시글 작성자")
        private String memberName;

        @Schema(description = "게시글 아이디")
        private Long boardId;

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

    }

    public static BoardItem toBoardItem(Board board, Status status) {
        BoardItemBuilder boardItemBuilder = BoardItem.builder()
            .memberName(board.getMember().getEmail().split("@")[0])
            .boardId(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .likeCount(board.getLikeCount())
            .likeStatus(status)
            .commentCount(board.getCommentCount())
            .registrationDate(board.getCreatedAt().toLocalDate());

        if (board instanceof EmploymentBoard employmentBoard) {
            boardItemBuilder
                .jobCategory(employmentBoard.getJobKeyword().getDescription())
                .build();
        } else if (board instanceof GroupBoard groupBoard) {
            boardItemBuilder
                .groupCategory(groupBoard.getGroupCategory().getDescription())
                .recruitment(groupBoard.getRecruitment())
                .peopleNumber(groupBoard.getPeopleNumber())
                .build();
        }

        return boardItemBuilder.build();
    }
}
