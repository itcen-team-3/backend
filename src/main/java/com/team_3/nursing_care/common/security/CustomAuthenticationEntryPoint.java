package com.team_3.nursing_care.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.team_3.nursing_care.common.exception.ResultMessage;
import com.team_3.nursing_care.common.response.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j(topic = "authentication entry point")
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Unauthorized access error: {}", authException.getMessage());

        ResponseDto<String> responseDto = new ResponseDto<>(HttpServletResponse.SC_UNAUTHORIZED, ResultMessage.Error, "Authentication Failed: " + authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.getWriter().write(objectMapper.writeValueAsString(responseDto));
    }
}
