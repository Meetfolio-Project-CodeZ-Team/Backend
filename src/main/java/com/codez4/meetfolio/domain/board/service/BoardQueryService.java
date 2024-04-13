package com.codez4.meetfolio.domain.board.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.dto.BoardResponse.BoardInfo;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.service.LikeQueryService;
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
public class BoardQueryService {

    private final BoardRepository boardRepository;
    private final LikeQueryService likeQueryService;

    // 내가 작성한 게시글 목록
    public BoardInfo findMyBoards(Member member, Integer page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<Board> myBoars = boardRepository.findAllByMember(member, pageRequest);
        List<Status> likeStatus = likeQueryService.getLikeStatus(member, myBoars.getContent());

        return BoardResponse.toBoardInfo(myBoars, likeStatus);
    }
}
