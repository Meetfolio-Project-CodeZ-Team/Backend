package com.codez4.meetfolio.domain.like.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.like.repository.LikeRepository;
import com.codez4.meetfolio.domain.member.Member;
import java.util.List;
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
    public BoardResponse.BoardInfo findMyLikedBoards(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<Like> pageLikes = likeRepository.findAllByMemberAndStatus(member, Status.ACTIVE,
            pageRequest);

        List<Status> likeStatus = pageLikes.stream()
            .map(Like::getStatus)
            .toList();

        return BoardResponse.toBoardInfo(pageLikes, likeStatus);

    }

    // 사용자에 따른 게시글의 좋아요 여부
    public List<Status> getLikeStatus(Member member, List<Board> boards) {

        return likeRepository.findStatusByMemberAndBoards(member, boards);
    }
}
