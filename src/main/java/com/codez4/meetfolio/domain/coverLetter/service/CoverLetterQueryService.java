package com.codez4.meetfolio.domain.coverLetter.service;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterResponse.CoverLetterInfo;
import com.codez4.meetfolio.domain.coverLetter.repository.CoverLetterRepository;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoverLetterQueryService {

    private final CoverLetterRepository coverLetterRepository;

    public CoverLetterInfo getCoverLetterInfo(Long coverLetterId) {

        CoverLetter coverLetter = findById(coverLetterId);
        return CoverLetterResponse.toCoverLetterInfo(coverLetter);
    }

    public CoverLetter findById(Long coverLetterId) {
        return coverLetterRepository.findById(coverLetterId).orElseThrow(
            () -> new ApiException(ErrorStatus._COVERLETTER_NOT_FOUND)
        );
    }

    public Page<CoverLetterResponse.CoverLetterItem> getMyCoverLetters(Member member,
        int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by("id").descending());
        Page<CoverLetter> coverLetters = coverLetterRepository.findActiveByMember(member,
            pageRequest);
        return CoverLetterResponse.toPageCoverLetterItem(coverLetters);
    }

    public Page<CoverLetterResponse.CoverLetterItem> getOtherCoverLetters(Member other,
        int page) {
        PageRequest pageRequest = PageRequest.of(page, 4, Sort.by("id").descending());

        Page<CoverLetter> otherCoverLetters = coverLetterRepository.findPublicAndActiveCoverLetterByMember(
            other,
            pageRequest);
        return CoverLetterResponse.toPageCoverLetterItem(otherCoverLetters);
    }
}
