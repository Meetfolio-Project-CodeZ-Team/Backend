package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.coverLetter.repository.CoverLetterRepository;
import com.codez4.meetfolio.domain.experience.repository.ExperienceRepository;
import com.codez4.meetfolio.domain.like.repository.LikeRepository;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberRequest;
import com.codez4.meetfolio.domain.member.dto.MemberResponse;
import com.codez4.meetfolio.domain.member.repository.MemberRepository;
import com.codez4.meetfolio.global.jwt.JwtTokenProvider;
import com.codez4.meetfolio.global.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final ExperienceRepository experienceRepository;
    private final CoverLetterRepository coverLetterRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

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

    public void deleteData(Member member) {
        commentRepository.deleteByMember(member);
        likeRepository.deleteByMember(member);
        boardRepository.deleteByMember(member);
        experienceRepository.deleteByMember(member);
        coverLetterRepository.findByMember(member).forEach(CoverLetter::delete);
        member.setInactive();
    }

}
