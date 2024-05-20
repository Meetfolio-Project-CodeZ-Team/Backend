package com.codez4.meetfolio.domain.comment.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse;
import com.codez4.meetfolio.domain.comment.dto.CommentResponse.CommentProc;
import com.codez4.meetfolio.domain.comment.dto.CommentVO;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final BoardQueryService boardQueryService;
    private final CommentQueryService commentQueryService;

    public CommentProc write(CommentVO commentVO) {

        Comment comment = commentRepository.save(createComment(commentVO));
        comment.getBoard().changeComment(true);

        return CommentResponse.toCommentProc(comment.getId());
    }

    private Comment createComment(CommentVO commentVO) {

        Member member = commentVO.member();
        Board board = boardQueryService.findById(commentVO.boardId());
        board.changeComment(true);
        String content = commentVO.content();
        Long parentId = commentVO.parentId();

        if (parentId != null) {
            Comment parentComment = getParentComment(parentId);
            int ref = commentRepository.getRefNumOfParent(board, parentId);
            int refOrder = commentRepository.getRefOrder(board, ref);
            int step = commentRepository.getStep(board, parentId);
            return CommentResponse.toEntity(member, board, content, parentComment, ref, refOrder+1, step+1);
        }
        else {
            int ref = commentRepository.getRefNum(board);
            return CommentResponse.toEntity(member, board, content, null, ref + 1, 0, 0);
        }
    }

    public Comment getParentComment(Long parentId) {
        return commentQueryService.findById(parentId);
    }

    public CommentResponse.CommentProc update(String content, Long commentId) {

        Comment comment = commentQueryService.findById(commentId);
        comment.update(content);
        return CommentResponse.toCommentProc(comment.getId());
    }

    public CommentResponse.CommentProc delete(Long commentId) {
        Comment comment = commentQueryService.findById(commentId);
        Board board  = comment.getBoard();
        board.changeComment(false);
        commentRepository.deleteById(commentId);
        return CommentResponse.toCommentProc(commentId);
    }
}
