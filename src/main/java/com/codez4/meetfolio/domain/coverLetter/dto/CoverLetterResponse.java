package com.codez4.meetfolio.domain.coverLetter.dto;

import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.member.Member;
import com.codez4.meetfolio.domain.member.dto.MemberResponse.MemberInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static com.codez4.meetfolio.domain.analysis.dto.AnalysisResponse.AnalysisInfo;
import static com.codez4.meetfolio.domain.feedback.dto.FeedbackResponse.FeedbackInfo;

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

        @Schema(description = "타 사용자 자소서 조회할 때, 포인트 사용 여부")
        private Boolean isPaid;
    }

    public static CoverLetterInfo toCoverLetterInfo(CoverLetter coverLetter, Boolean isPaid) {

        return CoverLetterInfo.builder()
                .coverLetterId(coverLetter.getId())
                .question(coverLetter.getQuestion())
                .answer(coverLetter.getAnswer())
                .shareType(coverLetter.getShareType().getDescription())
                .keyword1(coverLetter.getKeyword1())
                .keyword2(coverLetter.getKeyword2())
                .jobKeyword(coverLetter.getJobKeyword() != null ? coverLetter.getJobKeyword().getDescription() : null)
                .isPaid(isPaid)
                .build();

    }

    @Schema(description = "자기소개서 목록 정보 조회 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterListResult {
        private MemberInfo memberInfo;
        @Schema(description = "작성자 아이디")
        private String memberName;
        @Schema(description = "작성자 프로필")
        private String profile;
        private CoverLetterList coverLetterInfo;
    }

    @Schema(description = "타 사용자 자기소개서 목록 정보 조회 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class OtherMemberCoverLetterListResult {
        private MemberInfo memberInfo;
        @Schema(description = "작성자 아이디")
        private String memberName;
        @Schema(description = "작성자 프로필")
        private String profile;
        private CoverLetterList coverLetterInfo;
    }

    @Schema(description = "자기소개서 목록 정보 조회 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CoverLetterList {

        private List<CoverLetterItem> coverLetterInfo;

        @Schema(description = "페이징된 리스트의 항목 개수")
        private Integer listSize;

        @Schema(description = "총 페이징 수 ")
        private Integer totalPage;

        @Schema(description = "전체 데이터의 개수")
        private Long totalElements;

        @Schema(description = "첫 페이지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지의 여부")
        private Boolean isLast;
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

    public static CoverLetterListResult toCoverLetterListResult(MemberInfo memberInfo, CoverLetterList coverLetters) {
        return CoverLetterListResult.builder()
                .memberInfo(memberInfo)
                .coverLetterInfo(coverLetters)
                .build();
    }

    public static OtherMemberCoverLetterListResult toOtherMemberCoverLetterListResult(MemberInfo memberInfo, CoverLetterList coverLetters, Member other){
        return OtherMemberCoverLetterListResult.builder()
                .memberInfo(memberInfo)
                .coverLetterInfo(coverLetters)
                .memberName(other.getEmail().split("@")[0])
                .profile(other.getProfile().name())
                .build();
    }

    public static CoverLetterList toCoverLetterList(Page<CoverLetter> coverLetters) {
        List<CoverLetterItem> coverLetterItems = coverLetters.stream().map(CoverLetterResponse::toCoverLetterItem).toList();
        return CoverLetterList.builder()
                .coverLetterInfo(coverLetterItems)
                .listSize(coverLetterItems.size())
                .totalElements(coverLetters.getTotalElements())
                .totalPage(coverLetters.getTotalPages())
                .isFirst(coverLetters.isFirst())
                .isLast(coverLetters.isLast())
                .build();
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

        @Schema(description = "자기소개서 인덱스")
        private Long index;

        @Schema(description = "자기소개서 문항")
        private String question;

        @Schema(description = "자기소개서 문항 답변")
        private String answer;

        @Schema(description = "자소서 등록 날짜")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
    }

    public static CoverLetterItem toCoverLetterItem(CoverLetter coverLetter) {
        return CoverLetterItem.builder()
                .coverLetterId(coverLetter.getId())
                .index(coverLetter.getIndex())
                .question(coverLetter.getQuestion())
                .answer(coverLetter.getAnswer())
                .createdAt(coverLetter.getCreatedAt())
                .build();

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

