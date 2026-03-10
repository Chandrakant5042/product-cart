package com.basket.productapi.service;

import com.basket.productapi.dto.LoginRequest;
import com.basket.productapi.dto.LoginResponse;
import com.basket.productapi.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    ResponseEntity<LoginResponse> login(LoginRequest request, HttpServletResponse response);

    ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<LoginResponse> signup(RegisterRequest request);

    ResponseEntity<?> logout(HttpServletResponse response);

    ResponseEntity<Map<String, Object>> getProfile(Authentication authentication);
}