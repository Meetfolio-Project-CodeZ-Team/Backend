package com.codez4.meetfolio.domain.coverLetter.dto;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CoverLetterResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterInfo {

        private String question;
        private String answer;
        private String shareType;
        private String keyword1;
        private String keyword2;
        private String jobKeyword;
    }

    public static CoverLetterInfo toCoverLetterInfo(CoverLetter coverLetter) {

        return CoverLetterInfo.builder()
            .question(coverLetter.getQuestion())
            .answer(coverLetter.getAnswer())
            .shareType(coverLetter.getShareType().getDescription())
            .keyword1(coverLetter.getKeyword1())
            .keyword2(coverLetter.getKeyword2())
            .jobKeyword(coverLetter.getJobKeyword().getDescription())
            .build();

    }
}
