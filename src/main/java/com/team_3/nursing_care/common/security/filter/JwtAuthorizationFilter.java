package com.team_3.nursing_care.common.security.filter;

import com.team_3.nursing_care.common.security.user.UserDetailsProvider;
import com.team_3.nursing_care.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.team_3.nursing_care.common.util.JwtUtil.BEARER_PREFIX;

@Slf4j(topic = "jwt authorization filter")
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsProvider userDetailsProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            String jwtToken = jwtUtil.resolveToken(request);

            if (StringUtils.hasText(jwtToken))
                if (jwtToken.startsWith(BEARER_PREFIX)) {
                    String token = jwtToken.substring(BEARER_PREFIX.length()).trim();

                    if (jwtUtil.validationToken(token)) {
                        Authentication authentication = userDetailsProvider.getAuthentication(token);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
        }

        filterChain.doFilter(request, response);
    }
}
