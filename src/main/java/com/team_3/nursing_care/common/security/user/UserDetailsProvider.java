package com.team_3.nursing_care.common.security.user;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.common.util.JwtUtil;
import com.team_3.nursing_care.domain.member.constant.Role;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.team_3.nursing_care.common.util.JwtUtil.MEMBER_ID;
import static com.team_3.nursing_care.common.util.JwtUtil.ROLE;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsProvider {

    private final JwtUtil jwtUtil;
    private final MemberUserDetailsService memberUserDetailsService;

    public Authentication getAuthentication(String jwtToken) {
        Claims claims = jwtUtil.getClaims(jwtToken);
        String memberId = claims.get(MEMBER_ID).toString();
        String roles = claims.get(ROLE, String.class);

        if (roles == null) return null;

        boolean hasValidRole = false;
        String[] split = roles.split(",");

        for (String role : split) {
            log.info("{}: {}", ROLE, role);
            if (Arrays.stream(Role.values()).map(Role::getAuthority).anyMatch(role::equals)) {
                hasValidRole = true;
                break;
            }
        }

        if (!hasValidRole) return null;
        CustomUserDetails userDetails = (CustomUserDetails) memberUserDetailsService.loadUserByUsername(memberId);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
