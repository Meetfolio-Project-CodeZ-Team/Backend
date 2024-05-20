package com.codez4.meetfolio.domain.comment.repository;

import com.codez4.meetfolio.domain.board.Board;
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

    @Query("SELECT c.ref FROM Comment c WHERE c.board = :board and c.id = :parent")
    int getRefNumOfParent(Board board, Long parent);

    @Query("SELECT IFNULL(MAX(c.ref), 0) FROM Comment c WHERE c.board = :board")
    int getRefNum(Board board);

    @Query("SELECT IFNULL(MAX(c.refOrder), 0) FROM Comment c WHERE c.board = :board and c.ref = :ref")
    int getRefOrder(Board board, Integer ref);

    @Query("SELECT c.step FROM Comment c WHERE c.board = :board and c.id = :parent")
    int getStep(Board board, Long parent);
}
