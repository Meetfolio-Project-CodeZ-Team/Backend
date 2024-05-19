package com.codez4.meetfolio.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class CommentRequest {

    @Schema(description = "게시글 댓글 작성 요청 DTO")
    @Getter
    public static class Post {

        @Schema(description = "댓글 내용")
        private String content;

        @Schema(description = "부모 댓글 아이디 / 대댓글이 아닌 경우 안 보내도 됨")
        private Long parentId;
    }

    @Schema(description = "게시글 댓글 수정 요청 DTO")
    @Getter
    public static class PATCH {

        @Schema(description = "댓글 내용")
        private String content;

    }

}
