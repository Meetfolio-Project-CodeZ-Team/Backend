package com.codez4.meetfolio.domain.model.repository;

import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.model.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findTop3ByOrderByAccuracy();

    Page<Model> findAllByVersionStatusIsNull(Pageable pageable);

    Optional<Model> findModelByStatus(Status status);
}
