package com.codez4.meetfolio.domain.member.service;

import static com.codez4.meetfolio.domain.member.dto.MemberResponse.toMemberInfo;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public MemberInfo getMemberInfo(Long memberId) {
        return toMemberInfo(findById(memberId));
    }

}
