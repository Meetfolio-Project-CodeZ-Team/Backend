package com.codez4.meetfolio.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VersionStatus {
    DEPRECATED("삭제 요청"),
    OBSOLETE("삭제 완료")
    ;

    private final String description;
}
