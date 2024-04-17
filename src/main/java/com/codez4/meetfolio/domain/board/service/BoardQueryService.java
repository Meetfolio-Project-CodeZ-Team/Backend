package com.codez4.meetfolio.domain.board.service;

import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.board.repository.EmploymentBoardRepository;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.like.repository.LikeRepository;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardRepository boardRepository;
    private final EmploymentBoardRepository employmentBoardRepository;

    public SliceResponse<BoardResponse.BoardItem> getEmploymentBoards(Member member, int page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Slice<BoardQueryItem> employmentBoards =
                employmentBoardRepository.queryFindAllEmploymentBoards(member, pageable);
        Slice<BoardResponse.BoardItem> boards = employmentBoards.map(BoardResponse::toBoardItem);
        return new SliceResponse<>(boards);
    }

    public SliceResponse<BoardResponse.BoardItem> getEmploymentBoardsByJobKeyword(Member member, int page, JobKeyword jobKeyword) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Slice<BoardQueryItem> employmentBoards =
                employmentBoardRepository.queryFindAllEmploymentBoardsByJobKeyword(member, jobKeyword,pageable);
        Slice<BoardResponse.BoardItem> boards = employmentBoards.map(BoardResponse::toBoardItem);
        return new SliceResponse<>(boards);
    }

    public SliceResponse<BoardResponse.BoardItem> findMyBoards(Member member, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Slice<BoardQueryItem> boards = boardRepository.queryFindAllMyBoards(member, pageRequest);
        Slice<BoardResponse.BoardItem> myBoards = boards.map(BoardResponse::toBoardItem);
        return new SliceResponse<>(myBoards);
    }
}
