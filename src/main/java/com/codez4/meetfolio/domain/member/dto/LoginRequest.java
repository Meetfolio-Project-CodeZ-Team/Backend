package com.codez4.meetfolio.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "로그인 Request dto")
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
