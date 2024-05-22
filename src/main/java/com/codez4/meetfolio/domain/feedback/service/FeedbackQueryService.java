package com.codez4.meetfolio.domain.feedback.service;

import com.codez4.meetfolio.domain.feedback.Feedback;
import com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse;
import com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;
import com.codez4.meetfolio.domain.feedback.repository.FeedbackRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.codez4.meetfolio.global.response.code.status.ErrorStatus._FEEDBACK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackQueryService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackInfo getFeedbackInfo(Long coverLetterId) {

        return findByCoverLetterId(coverLetterId)
            .map(FeedbackResponse::toFeedbackInfo)
            .orElse(null);
    }

    public Optional<Feedback> findByCoverLetterId(Long coverLetterId) {
        return feedbackRepository.findByCoverLetterId(coverLetterId);
    }

    public Feedback findById(Long feedbackId){
        return feedbackRepository.findById(feedbackId).orElseThrow(()-> new ApiException(_FEEDBACK_NOT_FOUND));
    }
}
