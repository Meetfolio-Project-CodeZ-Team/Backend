package com.codez4.meetfolio.domain.solution;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Solution extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solution_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "cover_letter_id", nullable = false)
    private CoverLetter coverLetter;

    // TODO : AI 기능 확정 후 수정 필요
    @Column(name = "spell_check",nullable = false)
    private String spellCheck;

    @Column(nullable = false)
    private String correction;

    @Column(name="recommend_question_1",nullable = false)
    private String recommendQuestion1;

    @Column(name="recommend_question_2",nullable = false)
    private String recommendQuestion2;

    @Column(name="recommend_question_3",nullable = false)
    private String recommendQuestion3;
}
