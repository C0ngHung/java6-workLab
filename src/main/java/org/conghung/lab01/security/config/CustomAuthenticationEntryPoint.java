package org.conghung.lab01.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.conghung.lab01.dto.response.ApiResponse;
import org.conghung.lab01.exception.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .success(false)
                .error(ApiResponse.ErrorResponse.builder()
                        .code(ErrorCode.UNAUTHENTICATED.getCode())
                        .message(ErrorCode.UNAUTHENTICATED.getMessage())
                        .build())
                .build();

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}
