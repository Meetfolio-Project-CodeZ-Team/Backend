package com.codez4.meetfolio.domain.feedback;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.coverLetter.CoverLetter;
import com.codez4.meetfolio.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Feedback extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_letter_id", nullable = false)
    private CoverLetter coverLetter;

    @Column(columnDefinition = "TEXT")
    private String spellCheck;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String correction;

    @Column(name = "recommend_question_1", nullable = false)
    private String recommendQuestion1;

    @Column(name = "recommend_question_2", nullable = false)
    private String recommendQuestion2;

    @Column(name = "recommend_question_3", nullable = false)
    private String recommendQuestion3;

    @Column
    private Integer satisfaction;

    @Column(nullable = false)
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * update
     */
    public void delete() {
        this.status = Status.INACTIVE;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }
}
