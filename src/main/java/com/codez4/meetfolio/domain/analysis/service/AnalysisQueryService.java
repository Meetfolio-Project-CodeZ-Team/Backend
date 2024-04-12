package com.codez4.meetfolio.domain.analysis.service;

import com.codez4.meetfolio.domain.analysis.Analysis;
import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse;
import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import com.codez4.meetfolio.domain.analysis.repository.AnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisQueryService {

    private final AnalysisRepository analysisRepository;

    public AnalysisInfo getAnalysisInfo(Long coverLetterId) {

        return AnalysisResponse.toAnalysisInfo(findByCoverLetterId(coverLetterId));
    }

    public Analysis findByCoverLetterId(Long coverLetterId) {
        return analysisRepository.findByCoverLetterId(coverLetterId);
    }
}