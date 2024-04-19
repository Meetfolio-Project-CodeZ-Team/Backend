package com.codez4.meetfolio.global.jwt;

public interface JwtProperties {
    String TOKEN_PREFIX = "Bearer ";
    String ACCESS_HEADER_STRING = "Authorization";

    String REFRESH_HEADER_STRING = "RefreshToken";
}