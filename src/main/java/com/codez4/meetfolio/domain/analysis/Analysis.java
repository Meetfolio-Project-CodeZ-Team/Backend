package com.codez4.meetfolio.domain.analysis;

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
public class Analysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id",nullable = false)
    private Long id;

    @Column(name = "job_suitability", nullable = false)
    private double jobSuitability;

    @Column(name = "keyword_1",nullable = false)
    private String keyword1;

    @Column(name = "keyword_2",nullable = false)
    private String keyword2;

    @Column(name = "keyword_3",nullable =false)
    private String keyword3;

    @Column
    private Integer satisfaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_letter_id", nullable = false)
    private CoverLetter coverLetter;

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
