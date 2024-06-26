package com.codez4.meetfolio.domain.comment.dto;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.global.utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;

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
        MyCommentList commentInfo;
    }

    @Schema(description = "내 댓글 목록 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyCommentList {

        List<MyCommentItem> commentInfo;
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


    public static CommentResult toCommentResult(List<CommentItem> commentItems, Slice<Comment> comments) {

        return CommentResult.builder()
                .commentItems(commentItems)
                .hasNext(comments.hasNext())
                .isFirst(comments.isFirst())
                .isLast(comments.isLast())
                .build();
    }

    public static MyCommentResult toMyCommentResult(MemberResponse.MemberInfo memberInfo, Page<MyCommentItem> comments) {
        return MyCommentResult.builder()
                .memberInfo(memberInfo)
                .commentInfo(toMyCommentList(comments))
                .build();
    }

    public static MyCommentList toMyCommentList(Page<MyCommentItem> comments) {
        List<MyCommentItem> commentItems = comments.stream().toList();
        return MyCommentList.builder()
                .commentInfo(commentItems)
                .listSize(commentItems.size())
                .isFirst(comments.isFirst())
                .isLast(comments.isLast())
                .totalElements(comments.getTotalElements())
                .totalPage(comments.getTotalPages())
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

    public static MyCommentItem toMyCommentItem(Comment comment) {
        Board board = comment.getBoard();
        return MyCommentItem.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardCreatedAt(board.getCreatedAt())
                .build();
    }

    public static CommentItem toCommentItem(Comment comment, List<CommentItem> childComments) {

        Member member = comment.getMember();
        long sinceCreation = TimeUtils.getSinceCreation(comment.getCreatedAt());

        return CommentItem.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .memberName(member.getEmail().split("@")[0])
                .profile(member.getProfile().name())
                .sinceCreation(sinceCreation)
                .childComments(childComments)
                .build();
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

    public static Comment toEntity(Member member, Board board, String content,Comment parentComment, int ref, int refOrder, int
            step) {

        return Comment.builder()
                .member(member)
                .board(board)
                .content(content)
                .parentComment(parentComment)
                .ref(ref)
                .refOrder(refOrder)
                .step(step)
                .build();
    }


}
