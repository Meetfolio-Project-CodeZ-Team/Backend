package com.codez4.meetfolio.domain.dataset.repository;

import com.codez4.meetfolio.domain.dataset.Dataset;
import com.codez4.meetfolio.domain.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    Page<Dataset> getAllBy(PageRequest pageRequest);

    Integer countByStatus(Status status);
}
