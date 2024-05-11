package com.codez4.meetfolio.domain.dataset;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Status;
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
public class Dataset extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataset_id",nullable = false)
    private Long id;

    @Column(name = "job", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private JobKeyword jobKeyword;

    @Column
    private String url;

    @Column
    private String domain;
  
    @Column(length = 1000, nullable = false)
    private String data;

    @Column(nullable = false)
    @ColumnDefault("'INACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;
}
