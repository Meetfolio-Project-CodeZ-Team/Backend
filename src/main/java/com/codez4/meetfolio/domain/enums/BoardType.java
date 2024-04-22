package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    EMPLOYMENT,
    GROUP;

    @JsonCreator
    public static BoardType convert(String source)
    {
        for (BoardType boardType : BoardType.values()) {
            if (boardType.name().equals(source)) {
                return boardType;
            }
        }
        return null;
    }
}
