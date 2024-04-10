package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    void deleteByMember(Member member);
}
