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
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, gb.member.profile, (SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb WHERE gb.id = :boardId AND gb.member.status = 'ACTIVE'")
    BoardQueryItem queryFindBoard(Member member, Long boardId);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, gb.member.profile,(SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb WHERE gb.member.status = 'ACTIVE'")
    Page<BoardQueryItem> queryFindAllBoards(Member member, Pageable pageable);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, gb.member.profile,(SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb WHERE gb.groupCategory = :groupCategory AND gb.member.status = 'ACTIVE'")
    Page<BoardQueryItem> queryFindAllBoardsByGroupCategory(Member member, GroupCategory groupCategory, Pageable pageable);

    @Query(value = "SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, gb.member.profile, (SELECT l.status from Like l WHERE l.member = :member AND l.board = gb)) FROM GroupBoard gb WHERE gb.title LIKE CONCAT('%', :keyword , '%') AND gb.member.status = 'ACTIVE'")
    Page<BoardQueryItem> queryFindBoardsByKeyword(Member member, String keyword, Pageable pageable);
}
