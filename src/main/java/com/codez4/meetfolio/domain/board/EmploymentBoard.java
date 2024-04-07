package com.codez4.meetfolio.domain.board;

import com.codez4.meetfolio.domain.enums.JobKeyword;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("EMPLOYMENT")
public class EmploymentBoard extends Board{

    @Column(name = "job_category")
    @Enumerated(value = EnumType.STRING)
    private JobKeyword jobKeyword;
}
