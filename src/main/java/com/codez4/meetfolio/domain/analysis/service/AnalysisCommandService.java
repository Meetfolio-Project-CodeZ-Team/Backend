package com.codez4.meetfolio.domain.analysis.service;

import com.codez4.meetfolio.domain.analysis.Analysis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalysisCommandService {

    private final AnalysisQueryService analysisQueryService;

    public void softDelete(Long coverLetterId) {
        analysisQueryService.findByCoverLetterId(coverLetterId).ifPresent(Analysis::delete);
    }

    public void saveSatisfaction(Analysis analysis, int satisfaction){
        analysis.setSatisfaction(satisfaction);
    }
}
