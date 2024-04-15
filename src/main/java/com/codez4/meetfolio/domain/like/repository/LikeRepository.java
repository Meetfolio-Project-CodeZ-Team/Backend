package com.codez4.meetfolio.domain.like.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteByMember(Member member);

    @Query(value = "SELECT l FROM Like l JOIN fetch l.board WHERE l.member = :member AND l.status = :status",
            countQuery = "SELECT COUNT(l) FROM Like l INNER JOIN l.board WHERE l.member = :member AND l.status = :status")
    Page<Like> findAllByMemberAndStatus(@Param("member") Member member, @Param("status") Status status, PageRequest pageRequest);

    // 내가 작성한 게시글의 좋아요 여부
    @Query("SELECT l.status FROM Like l WHERE l.member = :member AND l.board IN :boards")
    List<Status> findStatusByMemberAndBoards(@Param("member") Member member,
        @Param("boards") List<Board> boards);
}
