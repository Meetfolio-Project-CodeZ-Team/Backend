package com.codez4.meetfolio.global.jwt;

import com.codez4.meetfolio.domain.enums.Authority;
import com.codez4.meetfolio.global.utils.RedisUtil;
import com.codez4.meetfolio.global.exception.ApiException;
import com.codez4.meetfolio.global.response.code.status.ErrorStatus;
import com.codez4.meetfolio.global.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
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

    @Value("${application.jwt.access_token_expire}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${application.jwt.refresh_token_expire}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private final CustomUserDetailService customUserDetailService;

    @Value("${application.jwt.secret_key}")
    private String secretKey;
    private final RedisUtil redisUtil;

    public JwtTokenProvider(@Value("${application.jwt.secret_key}")
                              String secretKey, CustomUserDetailService customUserDetailService, RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
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
    public String generateAccessToken(String email, Long memberId, Authority authority) {
        long now = (new Date()).getTime();
        Date expiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        return Jwts.builder()
                .setSubject(email)
                .claim(USER_ID_KEY, memberId)
                .claim(AUTHORITIES_KEY, authority.getDescription())
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken() {
        long now = (new Date()).getTime();
        Date expiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        Claims claims = Jwts
                .claims();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserId(String token) {
        return parseClaims(token)
                .get(USER_ID_KEY, Long.class);
    }

    public Long getExpiration(String token){

        Date expiration = Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token).getBody().getExpiration();

        long now = new Date().getTime();
        return expiration.getTime() - now;
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
            if(redisUtil.hasKeyBlackList(token)){
                throw new RuntimeException("이미 탈퇴한 회원입니다.");
            }
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 Access 토큰입니다.");
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
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(JwtProperties.ACCESS_HEADER_STRING, JwtProperties.TOKEN_PREFIX+ accessToken);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(JwtProperties.REFRESH_HEADER_STRING, JwtProperties.TOKEN_PREFIX+ refreshToken);
    }

    public boolean existsRefreshToken(String refreshToken) {
        return redisUtil.hasKey(refreshToken);
    }
}
