package com.codez4.meetfolio.domain.admin.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.enums.BoardType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;


public class BoardResponse {

    @Schema(description = "관리자 커뮤니티 게시글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardAdminResult {

        @Schema(description = "게시글 목록")
        private List<BoardAdminItem> boardInfo;

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


    @Schema(description = "관리자 커뮤니티 게시글 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BoardAdminItem {
        @Schema(description = "게시글 아이디")
        private Long boardId;

        @Schema(description = "게시글 등록 날짜")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;

        @Schema(description = "게시글 유형, EMPLOYMENT/GROUP")
        @Enumerated(EnumType.STRING)
        private BoardType boardType;

        @Schema(description = "게시글 작성자")
        private String memberName;

        @Schema(description = "게시글 제목")
        private String title;

    }

    public static BoardAdminResult toBoardAdminResult(Page<Board> boards) {
        List<BoardAdminItem> boardList = boards.stream().map(BoardResponse::toBoardAdminItem).toList();
        return BoardAdminResult.builder()
                .boardInfo(boardList)
                .listSize(boardList.size())
                .totalElements(boards.getTotalElements())
                .totalPage(boards.getTotalPages())
                .isFirst(boards.isFirst())
                .isLast(boards.isLast())
                .build();
    }

    public static BoardAdminItem toBoardAdminItem(Board board) {
        BoardResponse.BoardAdminItem.BoardAdminItemBuilder builder = BoardAdminItem.builder()
                .boardId(board.getId())
                .createdAt(board.getCreatedAt())
                .memberName(board.getMember().getEmail())
                .title(board.getTitle());
        if (board instanceof EmploymentBoard employmentBoard) {
            builder.boardType(BoardType.EMPLOYMENT);
        } else {
            builder.boardType(BoardType.GROUP);
        }
        return builder.build();
    }

}
