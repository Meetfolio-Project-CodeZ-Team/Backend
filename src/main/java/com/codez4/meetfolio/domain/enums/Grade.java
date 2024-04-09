package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {

    FRESHMAN("1학년"),
    SOPHOMORE("2학년"),
    JUNIOR("3학년"),
    SENIOR("4학년"),
    GRADUATE("졸업생")
    ;

    private final String description;

    @JsonCreator
    public static Grade convert(String source)
    {
        for (Grade grade : Grade.values()) {
            if (grade.name().equals(source)) {
                return grade;
            }
        }
        return null;
    }
}
