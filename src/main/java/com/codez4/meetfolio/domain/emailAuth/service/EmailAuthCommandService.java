package com.codez4.meetfolio.domain.emailAuth.service;

import com.codez4.meetfolio.domain.emailAuth.dto.EmailAuthRequest;
import com.codez4.meetfolio.domain.emailAuth.repository.EmailAuthRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailAuthCommandService {
    private final EmailAuthRepository emailAuthRepository;
    private final MailService mailService;

    public void save(EmailAuthRequest request) {
        emailAuthRepository.save(EmailAuthRequest.toEntity(request));
    }

    public void sendEmail(String email) {
        String title = "Meetfolio 이메일 인증 번호";
        String authCode =  createCode();
        mailService.sendEmail(email, title, authCode);

        save(new EmailAuthRequest(email, authCode));
    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new ApiException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

}
