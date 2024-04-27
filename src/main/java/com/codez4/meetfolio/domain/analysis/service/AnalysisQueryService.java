package com.codez4.meetfolio.domain.analysis.service;

import com.codez4.meetfolio.domain.analysis.Analysis;
import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse;
import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import com.codez4.meetfolio.domain.analysis.repository.AnalysisRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisQueryService {

    private final AnalysisRepository analysisRepository;

    public AnalysisInfo getAnalysisInfo(Long coverLetterId) {

        return findByCoverLetterId(coverLetterId)
            .map(AnalysisResponse::toAnalysisInfo)
            .orElse(null);
    }

    public Optional<Analysis> findByCoverLetterId(Long coverLetterId) {
        return analysisRepository.findByCoverLetterId(coverLetterId);
    }
}
