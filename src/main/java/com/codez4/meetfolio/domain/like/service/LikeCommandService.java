package com.codez4.meetfolio.domain.like.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.service.BoardQueryService;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.like.dto.LikeResponse;
import com.codez4.meetfolio.domain.like.repository.LikeRepository;
import com.codez4.meetfolio.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommandService {

    private final LikeRepository likeRepository;
    private final BoardQueryService boardQueryService;

    public LikeResponse changeLike(Member member, Long boardId) {

        Board board = boardQueryService.findById(boardId);

        Like like = likeRepository.findByMemberAndBoard(member, board)
            .map(Like::update)
            .orElseGet(() -> createLike(member, board));

        board.changeLike(like.getStatus());
        
        return LikeResponse.toLikeResponse(like);
    }

    private Like createLike(Member member, Board board) {

        return likeRepository.save(LikeResponse.toEntity(member, board));
    }


}
