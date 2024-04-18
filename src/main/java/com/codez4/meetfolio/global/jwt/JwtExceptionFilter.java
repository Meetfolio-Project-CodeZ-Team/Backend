package com.codez4.meetfolio.global.jwt;

import com.codez4.meetfolio.global.response.code.ErrorReasonDto;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException jwtException) {
            setErrorResponse(ErrorStatus._INVALID_TOKEN, response, jwtException);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            setErrorResponse(ErrorStatus._MEMBER_NOT_FOUND, response, usernameNotFoundException);
        }
    }

    public void setErrorResponse(
            ErrorStatus status,
            HttpServletResponse httpServletResponse,
            Throwable throwable
    ) throws IOException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json; charset=UTF-8");

        ErrorReasonDto jwtExceptionResponse = ErrorReasonDto.builder()
                .httpStatus(status.getHttpStatus())
                .isSuccess(false)
                .code(status.getCode())
                .message(throwable.getMessage())
                .build();

        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(jwtExceptionResponse));
    }
}