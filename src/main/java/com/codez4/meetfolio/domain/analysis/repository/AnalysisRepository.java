package com.codez4.meetfolio.domain.analysis.repository;

import com.codez4.meetfolio.domain.analysis.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    Analysis findByCoverLetterId(Long coverLetterId);
}
