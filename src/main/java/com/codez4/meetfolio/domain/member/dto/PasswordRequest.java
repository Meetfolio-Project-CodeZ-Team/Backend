package com.codez4.meetfolio.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "비밀번호 요청 dto")
@Getter
public class PasswordRequest {
    @NotBlank
    @Schema(description = "비밀번호")
    private String password;
}
