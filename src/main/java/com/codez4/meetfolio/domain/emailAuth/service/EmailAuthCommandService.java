package com.codez4.meetfolio.domain.emailAuth.service;

import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.utils.RedisUtil;
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
    private final MailService mailService;
    private final RedisUtil redisUtil;

    public void save(String email, String authCode) {
        redisUtil.setRedisEmailAuth(email, authCode, 1);
    }

    public void sendEmail(String email) {
        String title = "Meetfolio 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(email, title, authCode);
        save(email, authCode);
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
            log.debug("EmailAuthCommandService.createCode() exception occur");
            throw new ApiException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

}
