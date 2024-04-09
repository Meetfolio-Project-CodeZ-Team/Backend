package com.codez4.meetfolio.global.response.code.status;

import com.codez4.meetfolio.global.response.code.BaseErrorCode;
import com.codez4.meetfolio.global.response.code.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    //일반 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // ================================================================================================================= //

    // 사용자 관련
    _MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "존재하지 않는 사용자 정보입니다."),
    _MEMBER_EXISTS(HttpStatus.BAD_REQUEST, "MEMBER4002", "중복된 이메일입니다."),

    // ================================================================================================================= //

    // 이메일 인증 관련
    _AUTHENTICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTHENTICATION4001", "인증코드가 일치하지 않습니다."),
    _AUTHENTICATION_SEND_EMAIL_ERROR (HttpStatus.INTERNAL_SERVER_ERROR, "AUTHENTICATION4002", "인증 메일 전송에 실패했습니다."),

    // ================================================================================================================= //

    // 경험 분해 관련
    _EXPERIENCE_NOT_FOUND(HttpStatus.NOT_FOUND, "EXPERIENCE4001", "존재하지 않는 경험 분해 데이터입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
            .isSuccess(false)
            .code(code)
            .message(message)
            .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
            .httpStatus(httpStatus)
            .isSuccess(false)
            .code(code)
            .message(message)
            .build();
    }
}
