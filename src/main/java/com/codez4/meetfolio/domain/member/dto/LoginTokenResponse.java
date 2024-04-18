package com.codez4.meetfolio.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginTokenResponse {
    String accessToken;
    String refreshToken;
}
