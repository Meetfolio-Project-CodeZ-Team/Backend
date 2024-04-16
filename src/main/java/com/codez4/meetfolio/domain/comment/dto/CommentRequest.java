package com.codez4.meetfolio.domain.comment.dto;

import lombok.Getter;

public class CommentRequest {

    @Getter
    public static class Post {

        private String content;
        private Long parentId;
    }

}
