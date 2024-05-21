package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmploymentBoardRepository extends JpaRepository<EmploymentBoard, Long> {

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(eb, eb.member.email, eb.member.profile, (SELECT l.status FROM Like l WHERE l.member = :member AND eb = l.board)) FROM EmploymentBoard eb WHERE eb.id = :boardId AND eb.member.status = 'ACTIVE'")
    BoardQueryItem queryFindEmploymentBoard(Member member,Long boardId);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(eb, eb.member.email, eb.member.profile, (SELECT l.status FROM Like l WHERE l.member = :member AND eb = l.board)) FROM EmploymentBoard eb WHERE eb.member.status = 'ACTIVE'")
    Page<BoardQueryItem> queryFindAllEmploymentBoards(Member member, Pageable pageable);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(eb, eb.member.email, eb.member.profile, (SELECT l.status FROM Like l WHERE l.member = :member AND eb = l.board)) FROM EmploymentBoard eb WHERE eb.jobKeyword = :jobKeyword AND eb.member.status = 'ACTIVE'")
    Page<BoardQueryItem> queryFindAllEmploymentBoardsByJobKeyword(Member member, JobKeyword jobKeyword, Pageable pageable);

    @Query(value = "SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(eb, eb.member.email, eb.member.profile, (SELECT l.status from Like l WHERE l.member = :member AND l.board = eb)) FROM EmploymentBoard eb WHERE eb.title LIKE CONCAT('%', :keyword , '%') AND eb.member.status = 'ACTIVE'")
    Page<BoardQueryItem> queryFindBoardsByKeyword(Member member, String keyword, Pageable pageable);
}
