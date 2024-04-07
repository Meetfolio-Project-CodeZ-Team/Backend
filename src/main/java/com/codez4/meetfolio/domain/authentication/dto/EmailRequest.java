package com.codez4.meetfolio.domain.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "이메일 인증 코드 발급 요청 DTO")
@AllArgsConstructor
@Getter
public class EmailRequest {

    @NotBlank
    @Schema(description = "가천대 이메일", example = "meetfolio@gachon.ac.kr")
    private String email;

}
