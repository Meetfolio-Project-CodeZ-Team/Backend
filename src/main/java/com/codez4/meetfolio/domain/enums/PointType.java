package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {
    USE_COVER_LETTER("자소서 조회"),
    USE_AI_ANALYSIS("AI 직무 역량 분석"),
    CHARGE("충전");
    private final String description;

    @JsonCreator
    public static PointType convert(String source)
    {
        for (PointType pointType : PointType.values()) {
            if (pointType.name().equals(source)) {
                return pointType;
            }
        }
        return null;
    }
}
