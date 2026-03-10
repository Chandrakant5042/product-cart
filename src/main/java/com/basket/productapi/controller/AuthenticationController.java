package com.basket.productapi.controller;

import com.basket.productapi.dto.LoginRequest;
import com.basket.productapi.dto.LoginResponse;
import com.basket.productapi.dto.RegisterRequest;
import com.basket.productapi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {

		return authenticationService.login(request, response);
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {

		return authenticationService.refreshToken(request, response);
	}

	@PostMapping("/signup")
	public ResponseEntity<LoginResponse> signup(@RequestBody RegisterRequest request) {

		return authenticationService.signup(request);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		return authenticationService.logout(response);
	}

	@GetMapping("/profile")
	public ResponseEntity<Map<String, Object>> getProfile(Authentication authentication) {
		return authenticationService.getProfile(authentication);
	}
}