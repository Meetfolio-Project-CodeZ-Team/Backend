package com.codez4.meetfolio.domain.authentication.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender emailSender;

    // 인증 코드 메일 전송
    public void sendEmail(String email,
                          String title,
                          String text) {
        SimpleMailMessage emailForm = createEmailForm(email, title, text);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            log.info("MailService.sendEmail exception occur email: {}", e.getCause());
            throw new ApiException(ErrorStatus._AUTHENTICATION_SEND_EMAIL_ERROR);
        }
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String email,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

}

