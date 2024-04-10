package com.codez4.meetfolio.domain.member.service;

import static com.codez4.meetfolio.global.security.Password.ENCODER;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.LoginRequest;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.security.Password;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
            () -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND)
        );
    }

    private Optional<Member> findByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member;
    }

    public String login(LoginRequest request) {
        Member member = findByEmail(request.getEmail()).orElseThrow(
            () -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND));
        comparePassword(request.getPassword(), member.getPassword());
        String token = jwtTokenProvider.generate(member.getEmail(), member.getId(),
            member.getAuthority());
        return token;
    }

    public void checkDuplicatedEmail(String email) {
        Optional<Member> member = findByEmail(email);
        if (member.isPresent()) {
            throw new ApiException(ErrorStatus._MEMBER_EXISTS);
        }
    }

    private void comparePassword(String password, Password savedPassword) {
        if (!savedPassword.isSamePassword(password, ENCODER)) {
            throw new ApiException(ErrorStatus._INVALID_PASSWORD);
        }
    }
}
