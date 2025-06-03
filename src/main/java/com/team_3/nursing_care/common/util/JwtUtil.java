package com.team_3.nursing_care.common.util;

import com.team_3.nursing_care.domain.member.constant.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;

@Slf4j
@Component
public class JwtUtil {

    @Value("${spring.application.name}")
    private String issuer;
    @Value("${jwt.encoding.key}")
    private String key;
    @Value("${jwt.access.expiration_time}")
    private Long accessExpirationTime;
    @Value("${jwt.refresh.expiration_time}")
    private Long refreshExpirationTime;

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String MEMBER_ID = "memberId";
    public static final String COMPANY_ID = "companyId";
    public static final String ROLE = "role";


    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String createAccessToken(Long memberId, Long companyId, Set<Role> roles) {
        StringBuilder role = new StringBuilder();

        roles.stream().map(Role::getAuthority).forEach(auth -> role.append(auth).append(","));
        role.deleteCharAt(role.length() - 1);

        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .claim(COMPANY_ID, companyId)
                .claim(ROLE, role.toString())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpirationTime))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(Long memberId, Set<Role> roles) {
        StringBuilder role = new StringBuilder();

        roles.stream().map(Role::getAuthority).forEach(auth -> role.append(auth).append(","));
        role.deleteCharAt(role.length() - 1);

        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .claim(ROLE, role.toString())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader(REFRESH_TOKEN_HEADER);
    }

}
