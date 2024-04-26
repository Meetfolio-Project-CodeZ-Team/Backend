package com.codez4.meetfolio.domain.coverLetter.repository;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.member.Member;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {

    List<CoverLetter> findByMember(Member member);

    Slice<CoverLetter> findByMember(Member member, Pageable pageable);

    @Query("SELECT c FROM CoverLetter c WHERE c.member = :member and c.shareType = 'PUBLIC' and c.status = 'ACTIVE'")
    Slice<CoverLetter> findPublicAndActiveCoverLetterByMember(Member member, Pageable pageable);
}
