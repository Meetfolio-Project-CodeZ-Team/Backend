package com.codez4.meetfolio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileEmoji {
    BACKEND,
    WEB,
    APP,
    DESIGN,
    AI,
    TOOL,
    MAN,
    WOMAN,
    MOUSE,
    KEYBOARD,
    FIRE,
    SPARKLE;

    @JsonCreator
    public static ProfileEmoji convert(String source)
    {
        for (ProfileEmoji profileEmoji : ProfileEmoji.values()) {
            if (profileEmoji.name().equals(source)) {
                return profileEmoji;
            }
        }
        return null;
    }
}

