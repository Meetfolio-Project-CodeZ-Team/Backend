package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.TokenResponse;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
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

    private final MemberQueryService memberQueryService;
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
        redisUtil.setBlackList(accessToken, "accessToken", 1800L);
    }

    public TokenResponse reissue(String accessToken, String refreshToken){
        if(!jwtTokenProvider.validateToken(refreshToken)){
            throw new ApiException(ErrorStatus._INVALID_TOKEN);
        };
        Long memberId = Long.valueOf(redisUtil.get(refreshToken));
        log.info("memberId {}", memberId);
        Member member = memberQueryService.findById(memberId);
        return generateToken(member);
    }
}
