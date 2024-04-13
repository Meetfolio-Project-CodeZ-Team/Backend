package com.codez4.meetfolio.domain.board.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    void deleteByMember(Member member);

    Page<Board> findAllByMember(Member member, PageRequest pageRequest);
}
