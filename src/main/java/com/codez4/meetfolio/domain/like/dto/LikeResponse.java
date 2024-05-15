package com.codez4.meetfolio.domain.like.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LikeResponse {


    @Schema(description = "좋아요 처리 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LikeResult {
        @Schema(description = "게시물 ID")
        private Long boardId;

        @Schema(description = "게시물 좋아요 수")
        private int likeCount;

        @Schema(description = "좋아요 상태", example = "ACTIVE / INACTIVE")
        private String status;

        @Schema(description = "응답 생성 시간")
        private LocalDateTime createdAt;
    }

    @Schema(description = "좋아요 조화 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class LikeCountResult{
        @Schema(description = "게시물 ID")
        private Long boardId;

        @Schema(description = "게시물 좋아요 수")
        private int likeCount;
    }

    public static LikeResult toLikeResult(Board board, Like like) {
        return LikeResult.builder()
                .boardId(board.getId())
                .likeCount(board.getLikeCount())
                .status(like.getStatus().toString())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static LikeCountResult toLikeCountResult(Board board){
        return LikeCountResult.builder()
                .boardId(board.getId())
                .likeCount(board.getLikeCount())
                .build();
    }

    public static Like toEntity(Member member, Board board) {
        return Like.builder()
                .member(member)
                .board(board)
                .build();
    }
}
