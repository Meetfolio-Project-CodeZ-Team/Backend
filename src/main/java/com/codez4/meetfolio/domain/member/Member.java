package com.codez4.meetfolio.domain.member;

import com.codez4.meetfolio.domain.common.BaseTimeEntity;
import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.domain.enums.Grade;
import com.codez4.meetfolio.domain.enums.Major;
import com.codez4.meetfolio.domain.enums.Status;
import com.codez4.meetfolio.domain.jobKeyword.JobKeyword;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@DynamicInsert
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
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
    @ColumnDefault("0")
    private Integer point;

    @Column(nullable = false)
    @ColumnDefault("ACTIVE")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    @ColumnDefault("MEMBER")
    @Enumerated(EnumType.STRING)
    private Authority authority ;

    @Column(nullable = true)
    private LocalDateTime inactiveDate;

    @ManyToOne
    @Column(nullable = false)
    private JobKeyword jobKeyword;

}
