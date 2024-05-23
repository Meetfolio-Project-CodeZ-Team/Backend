package com.codez4.meetfolio.domain.analysis.service;

import com.codez4.meetfolio.domain.analysis.Analysis;
import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse;
import com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import com.codez4.meetfolio.domain.analysis.repository.AnalysisRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Analysis findById(Long analysisId){
        return analysisRepository.findById(analysisId).orElseThrow(()-> new ApiException(ErrorStatus._ANALYSIS_NOT_FOUND));
    }

    public Optional<Analysis> findByCoverLetterId(Long coverLetterId) {
        return analysisRepository.findByCoverLetterId(coverLetterId);
    }
}
