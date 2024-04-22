package com.codez4.meetfolio.domain.dataset;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.JobKeyword;
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
    @Column(name = "dataset_id",nullable = false)
    private Long id;

    @Column(name = "job")
    @Enumerated(value = EnumType.STRING)
    private JobKeyword jobKeyword;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String domain;
  
    @Column(length = 1000, nullable = false)
    private String data;
}
