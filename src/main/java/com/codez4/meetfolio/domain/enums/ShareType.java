package com.codez4.meetfolio.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShareType {

    PUBLIC("공개"),
    PRIVATE("비공개")
    ;

    private final String description;
}
