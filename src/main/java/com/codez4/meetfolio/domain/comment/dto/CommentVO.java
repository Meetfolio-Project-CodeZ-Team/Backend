package com.codez4.meetfolio.domain.comment.dto;

import com.codez4.meetfolio.domain.member.Member;

public record CommentVO(Member member, String content, Long parentId, Long boardId) {

    public static CommentVO of(Member member, CommentRequest.Post post, Long boardId) {
        return new CommentVO(member, post.getContent(), post.getParentId(), boardId);
    }
}
