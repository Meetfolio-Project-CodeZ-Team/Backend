package com.codez4.meetfolio.domain.comment.service;

import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.SliceResponse;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.codez4.meetfolio.domain.comment.dto.CommentResponse.toMyCommentItem;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;

    public SliceResponse<CommentResponse.MyCommentItem> findMyComments(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Slice<Comment> comments = commentRepository.findByMemberFetchJoinBoard(member, pageRequest);
        Slice<CommentResponse.MyCommentItem> myCommentItems = comments.map(CommentResponse::toMyCommentItem);
        return new SliceResponse<>(myCommentItems);
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

        return CommentResponse.toCommentResult(comments);
    }
}
