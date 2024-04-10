package com.codez4.meetfolio.domain.feedback.service;

import com.codez4.meetfolio.domain.feedback.Feedback;
import com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse;
import com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;
import com.codez4.meetfolio.domain.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackQueryService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackInfo getFeedbackInfo(Long coverLetterId) {

        return FeedbackResponse.toFeedbackInfo(findByCoverLetterId(coverLetterId));
    }
    public Feedback findByCoverLetterId(Long coverLetterId) {
        return feedbackRepository.findByCoverLetterId(coverLetterId);
    }
}
