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
        // 자식 댓글 삭제
        commentRepository.deleteByParentComment_Member(member);
        // 부모 댓글 삭제
        commentRepository.deleteByMember(member);
        // 게시물 삭제
        boardRepository.deleteByMember(member);
        // 경험 분해 삭제
        experienceRepository.deleteByMember(member);
        // 자소서 삭제
        coverLetterRepository.deleteByMember(member);
        // 회원 비활성화
        member.setInactive();
    }
}
