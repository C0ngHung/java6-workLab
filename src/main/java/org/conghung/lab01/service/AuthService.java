package org.conghung.lab01.service;

import org.conghung.lab01.dto.request.LoginRequest;
import org.conghung.lab01.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
