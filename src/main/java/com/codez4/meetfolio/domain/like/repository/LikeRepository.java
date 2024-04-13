package com.codez4.meetfolio.domain.like.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.like.Like;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteByMember(Member member);

    Page<Like> findAllByMemberAndStatus(Member member, Status status, PageRequest pageRequest);

    // 내가 작성한 게시글의 좋아요 여부
    Like findByMemberAndBoard(Member member, Board board);
}
