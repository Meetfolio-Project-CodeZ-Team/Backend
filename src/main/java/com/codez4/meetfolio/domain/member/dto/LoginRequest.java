package com.codez4.meetfolio.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "로그인 Request dto")
@Getter
public class LoginRequest {

    @NotBlank
    @Schema(description = "가천대 이메일", example = "meetfolio@gachon.ac.kr")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호")
    private String password;
}
