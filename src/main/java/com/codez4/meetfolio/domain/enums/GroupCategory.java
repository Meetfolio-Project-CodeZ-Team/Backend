package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupCategory {

    STUDY("스터디"),
    CONTEST("공모전")
    ;

    private final String description;

    @JsonCreator
    public static GroupCategory convert(String source)
    {
        for (GroupCategory groupCategory : GroupCategory.values()) {
            if (groupCategory.name().equals(source)) {
                return groupCategory;
            }
        }
        return null;
    }
}
