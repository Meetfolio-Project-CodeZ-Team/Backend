package com.codez4.meetfolio.domain.emailAuth.service;

import com.codez4.meetfolio.domain.emailAuth.EmailAuth;
import com.codez4.meetfolio.domain.emailAuth.dto.EmailAuthRequest;
import com.codez4.meetfolio.domain.emailAuth.repository.EmailAuthRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailAuthQueryService {

    private final EmailAuthRepository emailAuthRepository;

    public EmailAuth findByEmail(String email) {
        return emailAuthRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorStatus._AUTHENTICATION_NOT_FOUND)
        );
    }

    // 이메일 인증 코드 검증
    public String verifyAuthCode(EmailAuthRequest request) {
        EmailAuth emailAuth = findByEmail(request.getEmail());
        if (emailAuth.getCode().equals(request.getAuthCode())) return "재학생 이메일 인증이 성공하였습니다.";
        else throw new ApiException(ErrorStatus._AUTHENTICATION_NOT_FOUND);
    }

}
