package com.codez4.meetfolio.domain.member.service;

import com.codez4.meetfolio.domain.board.repository.BoardRepository;
import com.codez4.meetfolio.domain.comment.repository.CommentRepository;
import com.codez4.meetfolio.domain.coverLetter.repository.CoverLetterRepository;
import com.codez4.meetfolio.domain.experience.repository.ExperienceRepository;
import com.codez4.meetfolio.domain.feedback.repository.FeedbackRepository;
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
    private final ExperienceRepository experienceRepository;
    private final CoverLetterRepository coverLetterRepository;
    private final BoardRepository boardRepository;
    private final FeedbackRepository feedbackRepository;
    private final CommentRepository commentRepository;

    public Member save(MemberRequest.Post post) {
        return memberRepository.save(MemberRequest.toEntity(post));
    }

    public MemberResponse.MemberProc post(MemberRequest.Post post) {
        Member member = save(post);
        return MemberResponse.toMemberProc(member);
    }

    public void inactivateMember(Member member) {
        commentRepository.deleteByParentComment_Member(member);
        commentRepository.deleteByMember(member);
        boardRepository.deleteByMember(member);
        experienceRepository.deleteByMember(member);
        member.setInactive();
    }
}
