package com.codez4.meetfolio.domain.experience;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.experience.dto.ExperienceRequest;
import com.codez4.meetfolio.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Experience extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String experienceType;

    @Column(nullable = false)
    private String stack;

    @Column(nullable = false)
    private String task;

    @Column(nullable = false)
    private String motivation;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String advance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobKeyword jobKeyword;

    // update
    public void update(ExperienceRequest patch) {
        this.title = patch.getTitle();
        this.startDate = LocalDate.parse(patch.getStartDate());
        this.endDate = LocalDate.parse(patch.getEndDate());
        this.experienceType = patch.getExperienceType();
        this.task = patch.getTask();
        this.motivation = patch.getMotivation();
        this.jobKeyword = JobKeyword.valueOf(patch.getJobKeyword());
        this.stack = patch.getStack();
        this.detail = patch.getDetail();
        this.advance = patch.getAdvance();
    }
}
