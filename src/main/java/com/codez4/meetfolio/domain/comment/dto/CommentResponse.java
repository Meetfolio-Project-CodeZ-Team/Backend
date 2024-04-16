package com.codez4.meetfolio.domain.comment.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.member.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommentResult {

        private Long commentId;
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
