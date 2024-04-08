package com.codez4.meetfolio.domain.emailAuth.repository;

import com.codez4.meetfolio.domain.emailAuth.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    Optional<EmailAuth> findByEmail(String email);
}
