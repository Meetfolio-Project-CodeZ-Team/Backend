package com.codez4.meetfolio.domain.emailAuth.service;

import com.codez4.meetfolio.domain.emailAuth.dto.EmailAuthRequest;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailAuthQueryService {
    private final RedisUtil redisUtil;

    public String findByEmail(String email) {
        if(redisUtil.hasEmailAuthKey(email)){
            return redisUtil.getEmailAuth(email);
        }
        else throw new ApiException(ErrorStatus._AUTHENTICATION_NOT_FOUND);
    }

    // 이메일 인증 코드 검증
    public String verifyAuthCode(EmailAuthRequest request) {
        String authCode = findByEmail(request.getEmail());
        if (authCode.equals(request.getAuthCode())) return "재학생 이메일 인증이 성공하였습니다.";
        else throw new ApiException(ErrorStatus._AUTHENTICATION_NOT_FOUND);
    }

}
