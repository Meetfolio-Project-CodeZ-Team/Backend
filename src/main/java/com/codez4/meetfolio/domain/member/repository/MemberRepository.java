package com.codez4.meetfolio.domain.member.repository;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    int countMemberByStatusAndAuthority(Status status, Authority authority);

    int countMemberByStatusAndAuthorityAndJobKeyword(Status status, Authority authority, JobKeyword jobKeyword);

    Optional<Member> findByEmail(String email);

    Page<Member> findMemberByStatusAndAuthority(Status status, Authority authority, Pageable pageable);

    Page<Member> findMemberByStatusAndAuthorityAndJobKeyword(Status status, Authority authority, JobKeyword jobKeyword, Pageable pageable);

}
