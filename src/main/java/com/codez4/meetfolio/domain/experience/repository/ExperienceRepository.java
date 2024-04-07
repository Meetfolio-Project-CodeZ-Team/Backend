package com.codez4.meetfolio.domain.experience.repository;

import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    Page<Experience> findAllByMember(Member member, Pageable pageable);

}
