package com.codez4.meetfolio.domain.comment.service;

import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.codez4.meetfolio.domain.comment.dto.CommentResponse.toCommentItem;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;

    public Page<CommentResponse.MyCommentItem> findMyComments(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Comment> comments = commentRepository.findByMemberFetchJoinBoard(member, pageRequest);
        Page<CommentResponse.MyCommentItem> myCommentItems = comments.map(CommentResponse::toMyCommentItem);
        return myCommentItems;
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new ApiException(ErrorStatus._COMMENT_NOT_FOUND)
        );
    }

    public CommentResponse.CommentResult findCommentsByBoard(Long boardId, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());

        Slice<Comment> comments = commentRepository.findByBoardFetchJoinMember(
            boardId, pageRequest);

        // 부모 댓글만 반환하기 (자식 댓글은 부모 댓글 안에 담아서 전달)
        List<CommentResponse.CommentItem> parentComments = comments.stream()
                .filter(comment -> comment.getParentComment() == null)
                .map(comment -> toCommentItem(comment,commentRepository.findAllByBoard_IdAndRef(boardId,
                        comment.getRef()).stream().filter(child -> child.getRefOrder() != 0).map(child -> {return toCommentItem(child,new ArrayList<>());}).toList() ))
                .toList();

        return CommentResponse.toCommentResult(parentComments,comments);
    }
}
