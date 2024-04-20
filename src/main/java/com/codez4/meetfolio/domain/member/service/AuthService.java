package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.TokenResponse;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import com.codez4.meetfolio.global.utils.RedisUtil;
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

    public TokenResponse authorize(Member member) {
        TokenResponse tokenResponse = generateToken(member);
        redisUtil.set(tokenResponse.getRefreshToken(), String.valueOf(member.getId()), 180);
        return tokenResponse;
    }

    private TokenResponse generateToken(Member member) {
        String accessToken = jwtTokenProvider.generateAccessToken(member.getEmail(), member.getId(), member.getAuthority());
        String refreshToken = jwtTokenProvider.generateRefreshToken();
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String accessToken, String refreshToken){
        redisUtil.delete(refreshToken);
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisUtil.setBlackList(accessToken, "accessToken", expiration);
    }
}
