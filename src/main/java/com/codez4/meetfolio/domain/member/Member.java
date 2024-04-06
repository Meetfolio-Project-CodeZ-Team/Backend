package com.codez4.meetfolio.domain.member;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.JobKeyword;
import com.codez4.meetfolio.domain.enums.Major;
import com.codez4.meetfolio.domain.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id",nullable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Major major;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private Integer point;

    @Column(nullable = false)
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private String profile;

    @Column(nullable = false)
    @ColumnDefault("'MEMBER'")
    @Enumerated(EnumType.STRING)
    private Authority authority ;

    @Column
    private LocalDateTime inactiveDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobKeyword jobKeyword;

}
