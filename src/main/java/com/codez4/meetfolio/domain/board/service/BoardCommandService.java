package com.codez4.meetfolio.domain.board.service;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.board.dto.BoardRequest;
import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.board.repository.EmploymentBoardRepository;
import com.codez4.meetfolio.domain.board.repository.GroupBoardRepository;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.board.dto.BoardRequest.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommandService {
    private final BoardRepository boardRepository;
    private final EmploymentBoardRepository employmentBoardRepository;
    private final GroupBoardRepository groupBoardRepository;

    public EmploymentBoard saveEmploymentBoard(BoardRequest.EmploymentBoardRequest request, Member member) {
        return employmentBoardRepository.save(toEmploymentBoard(request, member));
    }

    public GroupBoard saveGroupBoard(BoardRequest.GroupBoardRequest request, Member member) {
        return groupBoardRepository.save(toGroupBoard(request, member));
    }

    public void updateBoard(Board board, BoardRequest.BoardUpdate request) {
        if (board instanceof EmploymentBoard employmentBoard) {
            if (JobKeyword.convert(request.getJobKeyword()) != null) {
                employmentBoard.update(toEmploymentBoardPatch(request));
            } else throw new ApiException(ErrorStatus._BAD_REQUEST);

        } else if (board instanceof GroupBoard groupBoard) {
            if (JobKeyword.convert(request.getGroupCategory()) != null) {
                groupBoard.update(toGroupBoardPatch(request));
            } else throw new ApiException(ErrorStatus._BAD_REQUEST);
        }
    }

    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }


}
