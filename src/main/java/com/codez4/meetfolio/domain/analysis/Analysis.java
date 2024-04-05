package com.codez4.meetfolio.domain.analysis;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Analysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id")
    private Long id;

    @Column(name = "job_suitability", precision =3, scale = 1, nullable = false)
    private BigDecimal jobSuitability;

    @Column(name = "keyword_1",nullable = true)
    private String keyword1;

    @Column(name = "keyword_2",nullable = true)
    private String keyword2;

    @Column(name = "keyword_3",nullable = true)
    private String keyword3;

    @Column(nullable = false)
    private Integer satisfaction;
}
