package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {
    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email,(SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb WHERE gb.id = :boardId")
    BoardQueryItem queryFindBoard(Member member, Long boardId);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email,(SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb")
    Page<BoardQueryItem> queryFindAllBoards(Member member, Pageable pageable);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, (SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb WHERE gb.groupCategory = :groupCategory ")
    Page<BoardQueryItem> queryFindAllBoardsByGroupCategory(Member member, GroupCategory groupCategory, Pageable pageable);

    @Query(value = "SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, (SELECT l.status from Like l WHERE l.member = :member AND l.board = gb)) FROM GroupBoard gb WHERE gb.title LIKE CONCAT('%', :keyword , '%') ")
    Page<BoardQueryItem> queryFindBoardsByKeyword(Member member, String keyword, Pageable pageable);
}
