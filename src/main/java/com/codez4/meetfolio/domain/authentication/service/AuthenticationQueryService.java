package com.codez4.meetfolio.domain.authentication.service;

import com.codez4.meetfolio.domain.authentication.Authentication;
import com.codez4.meetfolio.domain.authentication.dto.AuthenticationRequest;
import com.codez4.meetfolio.domain.authentication.repository.AuthenticationRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationQueryService {

    private final AuthenticationRepository authenticationRepository;

    public Authentication findByEmail(String email) {
        return authenticationRepository.findByEmail(email).orElseThrow(
                () -> new ApiException(ErrorStatus._AUTHENTICATION_NOT_FOUND)
        );
    }

    // 이메일 인증 코드 검증
    public String verifyAuthCode(AuthenticationRequest request) {
        Authentication authentication = this.findByEmail(request.getEmail());
        if (authentication.getCode().equals(request.getAuthCode())) return "재학생 이메일 인증이 성공하였습니다.";
        else throw new ApiException(ErrorStatus._AUTHENTICATION_NOT_FOUND);
    }

}
