package com.codez4.meetfolio.domain.analysis;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Integer satisfaction;
}
