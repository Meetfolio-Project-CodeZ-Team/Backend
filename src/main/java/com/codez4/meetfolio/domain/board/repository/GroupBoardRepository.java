package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.GroupBoard;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.enums.GroupCategory;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Long> {
    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email,(SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb")
    Slice<BoardQueryItem> queryFindAllGroupBoards(Member member, Pageable pageable);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(gb, gb.member.email, (SELECT l.status FROM Like l WHERE l.member = :member AND gb = l.board)) FROM GroupBoard gb WHERE gb.groupCategory = :groupCategory ")
    Slice<BoardQueryItem> queryFindAllGroupBoardsByGroupCategory(Member member, GroupCategory groupCategory, Pageable pageable);
}
