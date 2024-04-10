package com.codez4.meetfolio.domain.experience.repository;

import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.experience.Experience;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    void deleteByMember(Member member);

    Page<Experience> findAllByMember(Member member, Pageable pageable);

    List<Experience> findTop12ByJobKeywordOrderByIdDesc(JobKeyword jobKeyword);

    List<Experience> findTop12ByOrderByIdDesc();

}
