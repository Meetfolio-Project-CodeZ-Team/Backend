package com.codez4.meetfolio.domain.feedback.service;

import com.codez4.meetfolio.domain.analysis.Analysis;
import com.codez4.meetfolio.domain.feedback.Feedback;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackCommandService {

    private final FeedbackQueryService feedbackQueryService;

    public void softDelete(Long coverLetterId) {
        feedbackQueryService.findByCoverLetterId(coverLetterId).ifPresent(Feedback::delete);
    }

    public void saveSatisfaction(Feedback feedback, int satisfaction){
        feedback.setSatisfaction(satisfaction);
    }
}
