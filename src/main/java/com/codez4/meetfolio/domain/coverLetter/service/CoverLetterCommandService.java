package com.codez4.meetfolio.domain.coverLetter.service;

import com.codez4.meetfolio.domain.analysis.service.AnalysisCommandService;
import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterRequest;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterProc;
import com.codez4.meetfolio.domain.coverLetter.repository.CoverLetterRepository;
import com.codez4.meetfolio.domain.feedback.service.FeedbackCommandService;
import com.codez4.meetfolio.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoverLetterCommandService {

    private final CoverLetterRepository coverLetterRepository;
    private final CoverLetterQueryService coverLetterQueryService;
    private final AnalysisCommandService analysisCommandService;
    private final FeedbackCommandService feedbackCommandService;

    public CoverLetterProc write(Member member, CoverLetterRequest.Post request) {

        CoverLetter coverLetter = save(CoverLetterRequest.toEntity(member, request));

        return CoverLetterResponse.toCoverLetterProc(coverLetter.getId());

    }

    private CoverLetter save(CoverLetter coverLetter) {
        return coverLetterRepository.save(coverLetter);
    }

    public CoverLetterProc update(Long coverLetterId, CoverLetterRequest.Patch request) {
        CoverLetter coverLetter = coverLetterQueryService.findById(coverLetterId);
        coverLetter.update(request);
        return CoverLetterResponse.toCoverLetterProc(coverLetterId);
    }

    public CoverLetterProc softDelete(Long coverLetterId) {
        coverLetterQueryService.findById(coverLetterId).delete();
        analysisCommandService.softDelete(coverLetterId);
        feedbackCommandService.softDelete(coverLetterId);
        return CoverLetterResponse.toCoverLetterProc(coverLetterId);
    }

}
