package org.conghung.lab01.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.conghung.lab01.dto.request.LoginRequest;
import org.conghung.lab01.dto.response.ApiResponse;
import org.conghung.lab01.dto.response.LoginResponse;
import org.conghung.lab01.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/poly/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse result = authService.login(request);
        
        return ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login successful")
                .data(result)
                .build();
    }
}
