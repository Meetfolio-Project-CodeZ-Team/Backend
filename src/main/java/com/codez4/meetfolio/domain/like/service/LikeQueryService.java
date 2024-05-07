package com.codez4.meetfolio.domain.like.service;

import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.repository.LikeRepository;
import com.codez4.meetfolio.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeQueryService {

    private final LikeRepository likeRepository;

    // 내가 좋아요 한 게시글 목록
    public Page<BoardQueryItem> findMyLikedBoards(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<BoardQueryItem> likedBoards = likeRepository.findAllByMemberAndStatus(member, Status.ACTIVE, pageRequest);
        return likedBoards;

    }
}
