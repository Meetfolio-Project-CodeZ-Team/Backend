package com.codez4.meetfolio.domain.authentication.dto;

import com.codez4.meetfolio.domain.authentication.Authentication;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "가천대 재학생 인증 요청 DTO")
@AllArgsConstructor
@Getter
public class AuthenticationRequest {

    @NotBlank
    @Schema(description = "가천대 이메일", example = "meetfolio@gachon.ac.kr")
    private String email;

    @NotBlank
    @Schema(description = "인증 코드", example = "123456")
    private String authCode;

    public static Authentication toEntity(AuthenticationRequest request) {
        return Authentication.builder()
                .email(request.getEmail())
                .code(request.getAuthCode())
                .build();

    }
}
