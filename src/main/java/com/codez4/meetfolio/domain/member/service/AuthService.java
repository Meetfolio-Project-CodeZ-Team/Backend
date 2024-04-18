package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.LoginTokenResponse;
import com.codez4.meetfolio.global.utils.RedisUtil;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    public LoginTokenResponse authorize(Member member) {
        String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail(), member.getId(), member.getAuthority());
        String refreshToken = jwtTokenProvider.generateRefreshToken();
        log.info("access {}", accessToken);
        log.info("refresh {}", refreshToken);
        redisUtil.set(refreshToken, String.valueOf(member.getId()), 180);

        LoginTokenResponse response = LoginTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return response;
    }


    public void logout(String accessToken, String refreshToken){
        redisUtil.delete(refreshToken);
        redisUtil.setBlackList(accessToken, "accessToken", 1800L);
    }
}
