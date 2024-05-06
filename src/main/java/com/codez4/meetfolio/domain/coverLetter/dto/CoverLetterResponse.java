package com.codez4.meetfolio.domain.coverLetter.dto;

import static com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import static com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.codez4.meetfolio.global.response.SliceResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

public class CoverLetterResponse {

    @Schema(description = "자기소개서 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterInfo {

        @Schema(description = "자기소개서 아이디")
        private Long coverLetterId;

        @Schema(description = "자기소개서 문항")
        private String question;

        @Schema(description = "자기소개서 문항 답변")
        private String answer;

        @Schema(description = "자기소개서 공유 여부")
        private String shareType;

        @Schema(description = "강조하고 싶은 키워드 1")
        private String keyword1;

        @Schema(description = "강조하고 싶은 키워드 2")
        private String keyword2;

        @Schema(description = "지원 직무")
        private String jobKeyword;
    }

    public static CoverLetterInfo toCoverLetterInfo(CoverLetter coverLetter) {

        return CoverLetterInfo.builder()
                .coverLetterId(coverLetter.getId())
                .question(coverLetter.getQuestion())
                .answer(coverLetter.getAnswer())
                .shareType(coverLetter.getShareType().getDescription())
                .keyword1(coverLetter.getKeyword1())
                .keyword2(coverLetter.getKeyword2())
                .jobKeyword(coverLetter.getJobKeyword().getDescription())
                .build();

    }

    @Schema(description = "자기소개서 세부 정보 조회 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterResult {

        private MemberInfo memberInfo;
        private CoverLetterInfo coverLetterInfo;
        private FeedbackInfo feedbackInfo;
        private AnalysisInfo analysisInfo;
    }

    public static CoverLetterResult toCoverLetterResult(MemberInfo memberInfo,
                                                        CoverLetterInfo coverLetterInfo,
                                                        FeedbackInfo feedbackInfo, AnalysisInfo analysisInfo) {

        return CoverLetterResult.builder()
                .memberInfo(memberInfo)
                .coverLetterInfo(coverLetterInfo)
                .feedbackInfo(feedbackInfo)
                .analysisInfo(analysisInfo)
                .build();
    }

    @Schema(description = "자기소개서 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterItem {

        @Schema(description = "자기소개서 아이디")
        private Long coverLetterId;

        private Long index;

        @Schema(description = "자기소개서 문항")
        private String question;

        @Schema(description = "자기소개서 문항 답변")
        private String answer;

        @Schema(description = "자소서 등록 날짜")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
    }

    public static CoverLetterItem toCoverLetterItem(CoverLetter coverLetter, long index) {
        return CoverLetterItem.builder()
                .coverLetterId(coverLetter.getId())
                .index(index)
                .question(coverLetter.getQuestion())
                .answer(coverLetter.getAnswer())
                .createdAt(coverLetter.getCreatedAt())
                .build();

    }

    public static SliceResponse<CoverLetterItem> toSliceCoverLetterItem(Slice<CoverLetter> coverLetters) {

        Slice<CoverLetterItem> coverLetterItems = coverLetters.map(coverLetter -> {
            long index = coverLetters.getNumberOfElements() - coverLetters.getContent().indexOf(coverLetter) + 1;
            return toCoverLetterItem(coverLetter, index);
        });

        return new SliceResponse<>(coverLetterItems);
    }

    @Schema(description = "자기소개서 작성 & 수정 & 삭제 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter

    public static class CoverLetterProc {

        @Schema(description = "자기소개서 아이디")
        private Long coverLetterId;

        @Schema(description = "응답 처리 완료된 시간")
        private LocalDateTime createdAt;
    }

    public static CoverLetterProc toCoverLetterProc(Long coverLetterId) {
        return CoverLetterProc.builder()
                .coverLetterId(coverLetterId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

