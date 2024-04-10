package com.codez4.meetfolio.domain.feedback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackCommandService {

    private final FeedbackQueryService feedbackQueryService;

    public void softDelete(Long coverLetterId) {
        feedbackQueryService.findByCoverLetterId(coverLetterId).delete();
    }
}
