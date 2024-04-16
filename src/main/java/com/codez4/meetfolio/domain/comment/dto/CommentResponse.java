package com.codez4.meetfolio.domain.comment.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponse {

    @Schema(description = "댓글 처리 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommentResult {

        @Schema(description = "댓글 아이디")
        private Long commentId;

        @Schema(description = "응답 생성 시간")
        private LocalDateTime createdAt;
    }

    public static CommentResult toCommentResult(Comment comment) {

        return CommentResult.builder()
            .commentId(comment.getId())
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Comment toEntity(Member member, Board board, String content,
        Comment parentComment) {

        return Comment.builder()
            .member(member)
            .board(board)
            .content(content)
            .parentComment(parentComment)
            .build();
    }


}
