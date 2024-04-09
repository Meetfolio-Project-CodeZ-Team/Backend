package com.codez4.meetfolio.global.exception;

import com.codez4.meetfolio.global.response.code.ErrorReasonDto;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private final ErrorStatus errorStatus;

    public ApiException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorReasonDto getErrorReason() {
        return this.errorStatus.getReason();
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return this.errorStatus.getReasonHttpStatus();
    }
}
