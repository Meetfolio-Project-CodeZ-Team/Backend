package com.codez4.meetfolio.domain.emailAuth.dto;

import com.codez4.meetfolio.domain.emailAuth.EmailAuth;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "가천대 재학생 인증 요청 DTO")
@AllArgsConstructor
@Getter
public class EmailAuthRequest {

    @NotBlank
    @Schema(description = "가천대 이메일", example = "meetfolio@gachon.ac.kr")
    private String email;

    @NotBlank
    @Schema(description = "인증 코드", example = "123456")
    private String authCode;

    public static EmailAuth toEntity(String email, String authCode) {
        return EmailAuth.builder()
                .email(email)
                .code(authCode)
                .build();

    }
}
