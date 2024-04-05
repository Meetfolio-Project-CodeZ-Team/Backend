package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.enums.JobKeywordEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("EMPLOYMENT")
public class EmploymentBoard extends Board{

    @Column(name = "job_category",nullable = true)
    @Enumerated(value = EnumType.STRING)
    private JobKeywordEnum jobKeywordEnum;
}
