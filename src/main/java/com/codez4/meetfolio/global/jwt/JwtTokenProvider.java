package com.codez4.meetfolio.global.jwt;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String USER_ID_KEY = "id";
    private final Key key;

    @Value("${application.jwt.token_expire}")
    private long TOKEN_EXPIRE_TIME;
    private final CustomUserDetailService customUserDetailService;

    public JwtTokenProvider(@Value("${application.jwt.secret_key}") String secretKey,
                            CustomUserDetailService customUserDetailService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailService = customUserDetailService;
    }

    /**
     * JWT
     * payload "sub" : "email"
     * payload "id" : "userId"
     * payload "auth" : "MEMBER"
     * payload "iat" : "123456789"
     * payload "exp" : "123456789"
     * header "alg" : "HS512"
     */
    public String generate(String email, Long userId, Authority authority) {
        long now = (new Date()).getTime();
        Date expiredAt = new Date(now + TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .setSubject(email)
                .claim(USER_ID_KEY, userId)
                .claim(AUTHORITIES_KEY, authority)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserId(String token) {
        return parseClaims(token)
                .get(USER_ID_KEY, Long.class);
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrorStatus._INVALID_TOKEN);
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT 토큰이 잘못되었습니다.");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        if (claims.get(USER_ID_KEY, Long.class) == null) {
            throw new JwtException("권한 정보가 없는 토큰입니다.");
        }

        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.get(USER_ID_KEY, Long.class).toString());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
