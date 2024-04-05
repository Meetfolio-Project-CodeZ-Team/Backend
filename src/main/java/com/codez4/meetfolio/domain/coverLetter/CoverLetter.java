package com.codez4.meetfolio.domain.coverLetter;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.ShareType;
import com.codez4.meetfolio.domain.jobKeyword.JobKeyword;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CoverLetter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_letter_id")
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private Member member;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ShareType shareType;

    @ManyToOne
    @Column(nullable = true)
    private JobKeyword jobKeyword;

    @Column(name = "keyword_1",nullable = true)
    private String keyword1;

    @Column(name = "keyword_2",nullable = true)
    private String keyword2;
}
