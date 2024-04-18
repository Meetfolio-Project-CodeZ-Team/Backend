package com.codez4.meetfolio.domain.comment.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.dto.BoardResponse.BoardInfo;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;
    private final LikeQueryService likeQueryService;

    public BoardInfo findMyComments(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());

        // 페이징된 댓글 정보
        Page<Comment> pageComments = commentRepository.findByMemberFetchJoinBoard(member,
            pageRequest);

        // 중복된 게시글 제거하기
        List<Board> distinctBoards = getDistinctBoards(pageComments.getContent());

        PageImpl<Board> pagedBoards = new PageImpl<>(distinctBoards, pageRequest,
            distinctBoards.size());

        List<Status> likeStatus = likeQueryService.getLikeStatus(member, distinctBoards);

        return BoardResponse.toBoardInfo(pagedBoards, likeStatus);
    }

    private List<Board> getDistinctBoards(List<Comment> comments) {

        return comments.stream()
            .map(Comment::getBoard)
            .distinct()
            .toList();
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
