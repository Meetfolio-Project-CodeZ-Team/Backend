package com.codez4.meetfolio.domain.coverLetter.repository;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {

    List<CoverLetter> findByMember(Member member);

    @Query("SELECT c FROM CoverLetter c WHERE c.member = :member and c.status = 'ACTIVE'")
    Page<CoverLetter> findActiveByMember(Member member, Pageable pageable);

    @Query("SELECT c FROM CoverLetter c WHERE c.member = :member and c.shareType = 'PUBLIC' and c.status = 'ACTIVE'")
    Page<CoverLetter> findPublicAndActiveCoverLetterByMember(Member member, Pageable pageable);
    Optional<CoverLetter> findFirstByMemberOrderByIdDesc(Member member);
}
