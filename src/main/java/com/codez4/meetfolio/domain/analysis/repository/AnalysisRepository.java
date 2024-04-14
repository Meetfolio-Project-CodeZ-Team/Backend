package com.codez4.meetfolio.domain.analysis.repository;

import com.codez4.meetfolio.domain.analysis.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    long countAllBy();

    @Query("SELECT avg(a.satisfaction) FROM Analysis a")
    double queryGetAvgSatisfaction();

    Analysis findByCoverLetterId(Long coverLetterId);
}
