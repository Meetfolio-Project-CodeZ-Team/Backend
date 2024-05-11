package com.codez4.meetfolio.domain.board.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.board.dto.BoardResponse;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.board.repository.EmploymentBoardRepository;
import com.codez4.meetfolio.domain.board.repository.GroupBoardRepository;
import com.codez4.meetfolio.domain.enums.BoardType;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.board.dto.BoardResponse.toBoardItem;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryService {

    private final BoardRepository boardRepository;
    private final EmploymentBoardRepository employmentBoardRepository;
    private final GroupBoardRepository groupBoardRepository;

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new ApiException(ErrorStatus._BOARD_NOT_FOUND)
        );
    }

    public boolean isBoardMember(Long boardId, Member member) {
        return findById(boardId).getMember() == member;
    }

    public BoardResponse.BoardItem getEmploymentBoard(Member member, Long boardId) {
        BoardQueryItem boardQueryItem = employmentBoardRepository.queryFindEmploymentBoard(member, boardId);
        return toBoardItem(boardQueryItem);
    }

    public BoardResponse.BoardItem getGroupBoard(Member member, Long boardId) {
        BoardQueryItem boardQueryItem = groupBoardRepository.queryFindBoard(member, boardId);
        return toBoardItem(boardQueryItem);
    }

    public BoardResponse.BoardItem getBoard(Member member, Long boardId) {
        Board board = findById(boardId);
        if (board instanceof EmploymentBoard employmentBoard) {
            return getEmploymentBoard(member, employmentBoard.getId());
        } else if (board instanceof GroupBoard groupBoard) {
            return getGroupBoard(member, groupBoard.getId());
        } else return null;
    }


    public Page<BoardQueryItem> getEmploymentBoards(Member member, int page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Page<BoardQueryItem> employmentBoards =
                employmentBoardRepository.queryFindAllEmploymentBoards(member, pageable);
        return employmentBoards;
    }

    public Page<BoardQueryItem> getEmploymentBoardsByJobKeyword(Member member,
                                                                         int page, JobKeyword jobKeyword) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Page<BoardQueryItem> employmentBoards =
                employmentBoardRepository.queryFindAllEmploymentBoardsByJobKeyword(member, jobKeyword,
                        pageable);
        return employmentBoards;
    }

    public Page<BoardQueryItem> getGroupBoards(Member member, int page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Page<BoardQueryItem> groupBoards =
                groupBoardRepository.queryFindAllBoards(member, pageable);
        return groupBoards;
    }

    public Page<BoardQueryItem> getGroupBoardsByGroupCategory(Member member,
                                                                                int page, GroupCategory groupCategory) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Page<BoardQueryItem> groupBoards =
                groupBoardRepository.queryFindAllBoardsByGroupCategory(member, groupCategory,
                        pageable);
        return groupBoards;
    }

    public Page<BoardQueryItem> getMyBoards(Member member, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<BoardQueryItem> boards = boardRepository.queryFindAllMyBoards(member, pageRequest);
        return boards;
    }
  
    public Page<BoardQueryItem> getBoardsByKeyword(Member member, String keyword, BoardType type, int page) {

        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());
        Page<BoardQueryItem> boards;
        if (type == BoardType.EMPLOYMENT)
            boards = employmentBoardRepository.queryFindBoardsByKeyword(member, keyword, pageable);
        else boards = groupBoardRepository.queryFindBoardsByKeyword(member, keyword, pageable);
        return boards;
    }
}
