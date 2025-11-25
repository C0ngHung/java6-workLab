package org.conghung.lab01.service.impl;

import lombok.RequiredArgsConstructor;
import org.conghung.lab01.dto.request.LoginRequest;
import org.conghung.lab01.dto.response.LoginResponse;
import org.conghung.lab01.security.service.JwtService;
import org.conghung.lab01.service.AuthService;
import org.conghung.lab01.exception.AppException;
import org.conghung.lab01.exception.ErrorCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService securityJwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        var authInfo = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        var authentication = authenticationManager.authenticate(authInfo);
        
        if (authentication.isAuthenticated()) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = securityJwtService.create(user, 20 * 60);
            return LoginResponse.builder().token(token).build();
        }
        
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
}
