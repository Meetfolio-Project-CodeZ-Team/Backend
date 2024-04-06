package com.codez4.meetfolio.domain.model;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Model extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal version;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    @ColumnDefault("ACTIVE")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Double accuracy;

    @Column(nullable = false)
    private Double loss;

    @Column(nullable = false)
    private LocalDateTime learnedDate;

    @Column(nullable = false)
    private LocalDateTime activatedDate;
}
