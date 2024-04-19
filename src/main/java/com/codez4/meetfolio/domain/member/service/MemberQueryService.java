package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.LoginRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.security.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberInfo;
import static com.codez4.meetfolio.global.security.Password.ENCODER;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND)
        );
    }

    public Member checkEmailAndPassword(LoginRequest request) {
        log.debug("email {}" , request.getEmail());
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ApiException(ErrorStatus._MEMBER_NOT_FOUND));
        comparePassword(request.getPassword(), member.getPassword());
        return member;
    }

    private void comparePassword(String password, Password savedPassword) {
        if (!savedPassword.isSamePassword(password, ENCODER)) {
            throw new ApiException(ErrorStatus._INVALID_PASSWORD);
        }
    }

    public MemberInfo getMemberInfo(Long memberId) {
        return toMemberInfo(findById(memberId));
    }

    public void checkDuplicatedEmail(String email) {
        memberRepository.findByEmail(email).ifPresent(member -> {
            throw new ApiException(ErrorStatus._MEMBER_EXISTS);
        });
    }

    public MemberResponse.MemberDetailInfo getMyPage(Member member) {

        return MemberResponse.toMemberDetailInfo(member);
    }

}
