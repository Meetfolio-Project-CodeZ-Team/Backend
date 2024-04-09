package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Major {
    // TODO : 정책 확정 필요
    COMPUTER_ENGINEERING("컴퓨터 공학과");
    private final String description;

    @JsonCreator
    public static Major convert(String source) {
        for (Major major : Major.values()) {
            if (major.name().equals(source)) {
                return major;
            }
        }
        return null;
    }
}
