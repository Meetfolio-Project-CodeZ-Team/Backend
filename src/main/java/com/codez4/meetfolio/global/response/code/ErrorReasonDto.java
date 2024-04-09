package com.codez4.meetfolio.global.response.code;

import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorReasonDto {

    private HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public static ResponseEntity<ErrorReasonDto> toResponseEntity(ErrorStatus errorStatus) {
        return ResponseEntity
                .status(errorStatus.getHttpStatus())
                .body(
                        ErrorReasonDto.builder()
                                .httpStatus(errorStatus.getHttpStatus())
                                .isSuccess(false)
                                .code(errorStatus.getCode())
                                .message(errorStatus.getMessage())
                                .build()
                );
    }

}
