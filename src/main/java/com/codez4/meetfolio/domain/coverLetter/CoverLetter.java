package com.codez4.meetfolio.domain.coverLetter;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.coverLetter.dto.CoverLetterRequest;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.ShareType;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CoverLetter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_letter_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ShareType shareType;

    @Column(name = "keyword_1")
    private String keyword1;

    @Column(name = "keyword_2")
    private String keyword2;

    @Enumerated(EnumType.STRING)
    private JobKeyword jobKeyword;

    @Column(nullable = false)
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * update
     */
    public void update(CoverLetterRequest.Patch request) {
        this.question = request.getQuestion();
        this.answer = request.getAnswer();
        this.shareType = ShareType.convert(request.getShareType());
        this.keyword1 = request.getKeyword1();
        this.keyword2 = request.getKeyword2();
        this.jobKeyword = JobKeyword.convert(request.getJobKeyword());
    }

    public void delete() {
        this.status = Status.INACTIVE;
    }

}
