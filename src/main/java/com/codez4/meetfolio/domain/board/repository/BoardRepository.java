package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    void deleteByMember(Member member);

    @Query(value = "SELECT Board FROM Board b WHERE b.title LIKE CONCAT('%', :keyword , '%') ")
    Page<Board> queryFindBoardsByKeyword(String keyword, Pageable pageable);

    @Query(value = "SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(b, b.member.email, (SELECT l.status from Like l WHERE l.member = :member AND l.board = b)) FROM Board b WHERE b.member = :member")
    Page<BoardQueryItem> queryFindAllMyBoards(Member member, Pageable pageable);

}
