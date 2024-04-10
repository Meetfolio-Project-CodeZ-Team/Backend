package com.codez4.meetfolio.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {

    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private final String description;

}
