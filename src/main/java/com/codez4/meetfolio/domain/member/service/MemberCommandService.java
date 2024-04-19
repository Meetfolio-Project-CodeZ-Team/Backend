package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;

    public Member save(MemberRequest.Post post) {
        return memberRepository.save(MemberRequest.toEntity(post));
    }

    public MemberResponse.MemberProc post(MemberRequest.Post post) {
        Member member = save(post);
        return MemberResponse.toMemberProc(member);
    }


    public MemberResponse.MemberProc update(Member member, MemberRequest.Patch patch) {
        member.update(patch);
        return MemberResponse.toMemberProc(member);
    }

    public MemberResponse.MemberProc update(Member member, MemberRequest.Patch patch) {
        member.update(patch);
        return MemberResponse.toMemberProc(member);
    }
}
