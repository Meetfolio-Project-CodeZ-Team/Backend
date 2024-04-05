package com.codez4.meetfolio.domain.dataset;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.JobKeywordEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Dataset extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataset_id")
    private Long id;

    @Column(name = "job")
    @Enumerated(value = EnumType.STRING)
    private JobKeywordEnum jobKeywordEnum;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String domain;
}
