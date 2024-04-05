package com.codez4.meetfolio.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupCategory {

    STUDY("스터디"),
    CONTEST("공모전")
    ;

    private final String description;
}
