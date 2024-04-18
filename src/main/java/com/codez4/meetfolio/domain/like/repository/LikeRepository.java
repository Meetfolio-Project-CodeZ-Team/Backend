package com.codez4.meetfolio.domain.like.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.board.dto.BoardQueryItem;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteByMember(Member member);

    @Query("SELECT " +
            "NEW com.codez4.meetfolio.domain.board.dto.BoardQueryItem(lb, lb.member.email, l.status) FROM Like l JOIN fetch Board lb ON l.board = lb  WHERE l.member = :member AND l.status = :status")
    Slice<BoardQueryItem> findAllByMemberAndStatus(@Param("member") Member member, @Param("status") Status status, PageRequest pageRequest);

    // 내가 작성한 게시글의 좋아요 여부
    @Query("SELECT l.status FROM Like l WHERE l.member = :member AND l.board IN :boards")
    List<Status> findStatusByMemberAndBoards(@Param("member") Member member,
                                             @Param("boards") List<Board> boards);
}
