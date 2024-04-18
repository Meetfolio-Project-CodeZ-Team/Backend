package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.EmploymentBoard;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmploymentBoardRepository extends JpaRepository<EmploymentBoard, Long> {

    Slice<EmploymentBoard> findSliceBy(Pageable pageable);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(eb, eb.member.email,(SELECT l.status FROM Like l WHERE l.member = :member AND eb = l.board)) FROM EmploymentBoard eb")
    Slice<BoardQueryItem> queryFindAllEmploymentBoards(Member member, Pageable pageable);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(eb, eb.member.email, (SELECT l.status FROM Like l WHERE l.member = :member AND eb = l.board)) FROM EmploymentBoard eb WHERE eb.jobKeyword = :jobKeyword ")
    Slice<BoardQueryItem> queryFindAllEmploymentBoardsByJobKeyword(Member member, JobKeyword jobKeyword, Pageable pageable);
}