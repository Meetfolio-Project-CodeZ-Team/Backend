package com.codez4.meetfolio.domain.feedback.repository;

import com.codez4.meetfolio.domain.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Feedback findByCoverLetterId(Long coverLetterId);
}
