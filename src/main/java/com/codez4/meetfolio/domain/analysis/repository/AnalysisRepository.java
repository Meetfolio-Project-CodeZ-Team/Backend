package com.codez4.meetfolio.domain.analysis.repository;

import com.codez4.meetfolio.domain.analysis.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    long countAllBy();

    @Query("SELECT IFNULL(avg(a.satisfaction),0) FROM Analysis a")
    double queryGetAvgSatisfaction();

    Optional<Analysis> findByCoverLetterId(Long coverLetterId);
}
