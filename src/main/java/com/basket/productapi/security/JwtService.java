package com.basket.productapi.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

	String extractUsername(String token);
	boolean isTokenValid(String token, UserDetails userDetails);
	String generateAccessToken(UserDetails userDetails);
	String generateRefreshToken(UserDetails userDetails);
}
