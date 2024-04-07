package com.codez4.meetfolio.domain.authentication.repository;

import com.codez4.meetfolio.domain.authentication.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Optional<Authentication> findByEmail(String email);
}
