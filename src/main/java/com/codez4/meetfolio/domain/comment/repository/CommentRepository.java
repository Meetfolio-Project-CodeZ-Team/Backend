package com.codez4.meetfolio.domain.comment.repository;

import com.codez4.meetfolio.domain.board.Board;
import com.codez4.meetfolio.domain.comment.Comment;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    void deleteByParentComment_Member(Member parentComment_member);
    void deleteByMember(Member member);
}
