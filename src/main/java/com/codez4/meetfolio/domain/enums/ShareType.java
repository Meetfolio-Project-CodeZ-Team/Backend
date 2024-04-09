package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShareType {

    PUBLIC("공개"),
    PRIVATE("비공개")
    ;

    private final String description;

    @JsonCreator
    public static ShareType convert(String source)
    {
        for (ShareType shareType : ShareType.values()) {
            if (shareType.name().equals(source)) {
                return shareType;
            }
        }
        return null;
    }
}
