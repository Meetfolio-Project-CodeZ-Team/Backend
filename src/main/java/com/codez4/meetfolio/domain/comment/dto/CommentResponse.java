package com.codez4.meetfolio.domain.comment.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.global.response.SliceResponse;
import com.codez4.meetfolio.global.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentResponse {

    @Schema(description = "댓글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommentResult {

        @Schema(description = "댓글 데이터 목록")
        List<CommentItem> commentItems;

        @Schema(description = "추가 조회할 목록이 있는지의 여부")
        private boolean hasNext;

        @Schema(description = "첫 페이지인지 여부")
        private boolean isFirst;

        @Schema(description = "마지막 페이지인지 여부")
        private boolean isLast;
    }

    @Schema(description = "내 댓글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyCommentResult {

        @Schema(description = "로그인 사용자 정보")
        private MemberResponse.MemberInfo memberInfo;

        @Schema(description = "댓글 데이터 목록")
        SliceResponse<MyCommentItem> commentInfo;
    }

    public static CommentResult toCommentResult(Slice<Comment> comments) {

        // 부모 댓글만 반환하기 (자식 댓글은 부모 댓글 안에 담아서 전달)
        List<CommentItem> commentItems = comments.stream()
            .filter(comment -> comment.getParentComment() == null)
            .map(CommentResponse::toCommentItem)
            .toList();

        return CommentResult.builder()
            .commentItems(commentItems)
            .hasNext(comments.hasNext())
            .isFirst(comments.isFirst())
            .isLast(comments.isLast())
            .build();
    }

    public static MyCommentResult toMyCommentResult(MemberResponse.MemberInfo memberInfo,SliceResponse<MyCommentItem> comments) {
        return MyCommentResult.builder()
                .memberInfo(memberInfo)
                .commentInfo(comments)
                .build();
    }

    @Schema(description = "댓글 데이터 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommentItem {

        @Schema(description = "댓글 아이디")
        private Long commentId;

        @Schema(description = "댓글 내용")
        private String content;

        @Schema(description = "댓글 작성자 이름")
        private String memberName;

        @Schema(description = "댓글 작성자 프로필")
        private String profile;

        @Schema(description = "댓글이 작성된 시간으로부터의 시간")
        private long sinceCreation;

        @Schema(description = "자식 댓글 데이터 목록")
        private List<CommentItem> childComments;
    }

    @Schema(description = "댓글 데이터 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyCommentItem {

        @Schema(description = "댓글 아이디")
        private Long commentId;

        @Schema(description = "댓글 내용")
        private String content;

        @Schema(description = "게시글 아이디")
        private Long boardId;

        @Schema(description = "게시글 제목")
        private String boardTitle;

        @Schema(description = "게시글 작성 시간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime boardCreatedAt;

    }

    public static MyCommentItem toMyCommentItem(Comment comment){
        Board board = comment.getBoard();
        return MyCommentItem.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardCreatedAt(board.getCreatedAt())
                .build();
    }

    public static CommentItem toCommentItem(Comment comment) {

        Member member = comment.getMember();
        long sinceCreation = TimeUtils.getSinceCreation(comment.getCreatedAt());

        return CommentItem.builder()
            .commentId(comment.getId())
            .content(comment.getContent())
            .memberName(member.getEmail().split("@")[0])
            .profile(member.getProfile())
            .sinceCreation(sinceCreation)
            .childComments(getChildList(comment))
            .build();
    }

    private static List<CommentItem> getChildList(Comment comment) {

        return Optional.ofNullable(comment.getChildren())
            .orElse(new ArrayList<>())
            .stream()
            .map(CommentResponse::toCommentItem)
            .toList();
    }

    @Schema(description = "댓글 처리 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CommentProc {

        @Schema(description = "댓글 아이디")
        private Long commentId;

        @Schema(description = "응답 생성 시간")
        private LocalDateTime createdAt;
    }

    public static CommentProc toCommentProc(Long commentId) {

        return CommentProc.builder()
            .commentId(commentId)
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
