package com.codez4.meetfolio.domain.comment.repository;

import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByMember(Member member);

    @Query(value = "SELECT c FROM Comment c JOIN FETCH Board b ON b = c.board WHERE c.member = :member ")
    Page<Comment> findByMemberFetchJoinBoard(@Param("member") Member member,
                                             PageRequest pageRequest);

    @Query("SELECT c FROM Comment c JOIN FETCH c.member where c.board.id = :boardId")
    Slice<Comment> findByBoardFetchJoinMember(@Param("boardId") Long boardId,
        PageRequest pageRequest);
}
