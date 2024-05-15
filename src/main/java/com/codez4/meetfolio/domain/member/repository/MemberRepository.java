package com.codez4.meetfolio.domain.member.repository;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    int countMemberByStatusAndAuthority(Status status, Authority authority);

    int countMemberByStatusAndAuthorityAndJobKeyword(Status status, Authority authority, JobKeyword jobKeyword);

    Optional<Member> findByEmail(String email);

    Page<Member> findMemberByAuthorityAndEmailIsNot(Authority authority, String email, Pageable pageable);

    Page<Member> findMemberByAuthorityAndJobKeywordAndEmailIsNot(Authority authority, JobKeyword jobKeyword, String email, Pageable pageable);

    @Query("SELECT m FROM Member m WHERE SUBSTRING(m.email,1,length(m.email) -13 ) LIKE concat('%',:keyword,'%') AND m.authority =:authority")
    Page<Member> queryFindMemberByAuthorityAndKeywordAndEmailIsNotEmpty(Authority authority,String keyword, Pageable pageable);

}
