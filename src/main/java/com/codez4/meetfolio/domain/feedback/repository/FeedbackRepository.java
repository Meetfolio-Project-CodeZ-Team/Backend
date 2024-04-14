package com.codez4.meetfolio.domain.feedback.repository;

import com.codez4.meetfolio.domain.feedback.Feedback;
import com.codez4.meetfolio.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    long countAllBy();

    Feedback findByCoverLetterId(Long coverLetterId);

    void deleteByCoverLetter_Member(Member coverLetter_member);

    @Query("SELECT avg(f.satisfaction) FROM Feedback f")
    double queryGetAvgSatisfaction();
}
