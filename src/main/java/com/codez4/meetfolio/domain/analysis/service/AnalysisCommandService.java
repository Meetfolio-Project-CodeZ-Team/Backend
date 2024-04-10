package com.codez4.meetfolio.domain.analysis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalysisCommandService {

    private final AnalysisQueryService analysisQueryService;

    public void softDelete(Long coverLetterId) {
        analysisQueryService.findByCoverLetterId(coverLetterId).delete();
    }
}
