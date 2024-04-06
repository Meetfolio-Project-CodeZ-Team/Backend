package com.codez4.meetfolio.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobKeyword {

    BACKEND("백엔드"),
    WEB("웹개발"),
    APP("앱개발"),
    DESIGN("디자인"),
    AI("AI")
    ;

    private final String description;
}
