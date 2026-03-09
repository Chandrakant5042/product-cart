package com.basket.productapi.controller;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.basket.productapi.dto.AuthRequest;
import com.basket.productapi.dto.AuthResponse;
import com.basket.productapi.dto.LoginRequest;
import com.basket.productapi.dto.LoginResponse;
import com.basket.productapi.dto.ProductResponse;
import com.basket.productapi.dto.RegisterRequest;
import com.basket.productapi.entity.User;
import com.basket.productapi.repository.UserRepository;
import com.basket.productapi.security.JwtService;
import com.basket.productapi.service.ProductService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
    private ProductService productService;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	 @PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody AuthRequest request,
	                                HttpServletResponse response) {

	     Authentication authentication = authenticationManager.authenticate(
	             new UsernamePasswordAuthenticationToken(
	                     request.username(),
	                     request.password()
	             )
	     );

	     UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	     String accessToken = jwtService.generateAccessToken(userDetails);
	     String refreshToken = jwtService.generateRefreshToken(userDetails);

	     // 🔐 Access Token Cookie
	     ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
	             .httpOnly(true)
	             .secure(false) // 🔥 change to true in production (HTTPS)
	             .path("/")
	             .maxAge(Duration.ofMinutes(15))
	             .sameSite("Strict")
	             .build();

	     // 🔐 Refresh Token Cookie
	     ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
	             .httpOnly(true)
	             .secure(false) // 🔥 change to true in production
	             .path("/api/v1/refresh")
	             .maxAge(Duration.ofDays(7))
	             .sameSite("Strict")
	             .build();

	     response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
	     response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

	     return ResponseEntity.ok("Login successful");
	 }
	 
	 @PostMapping("/refresh")
	 public ResponseEntity<?> refreshToken(HttpServletRequest request,
	                                       HttpServletResponse response) {

	     String refreshToken = null;

	     if (request.getCookies() != null) {
	         for (Cookie cookie : request.getCookies()) {
	             if ("refreshToken".equals(cookie.getName())) {
	                 refreshToken = cookie.getValue();
	             }
	         }
	     }

	     if (refreshToken == null) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	     }

	     String username = jwtService.extractUsername(refreshToken);
	     UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	     if (!jwtService.isTokenValid(refreshToken, userDetails)) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	     }

	     String newAccessToken = jwtService.generateAccessToken(userDetails);

	     ResponseCookie newAccessCookie = ResponseCookie.from("accessToken", newAccessToken)
	             .httpOnly(true)
	             .secure(false)
	             .path("/")
	             .maxAge(Duration.ofMinutes(15))
	             .sameSite("Lax")
	             .build();

	     response.addHeader(HttpHeaders.SET_COOKIE, newAccessCookie.toString());

	     return ResponseEntity.ok("Token refreshed");
	 }
	 
	 @PostMapping("/signup")
	 public ResponseEntity<AuthResponse> signup(@RequestBody RegisterRequest request) {

	     // 1. Check if user already exists
	     if (userRepository.findByUsername(request.username()).isPresent()) {
	         throw new RuntimeException("Username already exists");
	     }

	     // 2. Create new user
	     User user = new User();
	     user.setUsername(request.username());

	     // IMPORTANT: Encode password
	     user.setPassword(passwordEncoder.encode(request.password()));

	     // Default role if not provided
	     String role = request.role() != null ? request.role() : "USER";
	     user.setRole(role.toUpperCase());

	     userRepository.save(user);

	     // 3. Generate tokens
	     UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

	     String accessToken = jwtService.generateAccessToken(userDetails);
	     String refreshToken = jwtService.generateRefreshToken(userDetails);

	     return ResponseEntity.ok(
	             new AuthResponse(accessToken, refreshToken)
	     );
	 }
	 
	 @PostMapping("/logout")
	 public ResponseEntity<?> logout(HttpServletResponse response) {

	     ResponseCookie cookie = ResponseCookie.from("accessToken", "")
	             .httpOnly(true)
	             .secure(false)
	             .path("/")
	             .maxAge(0)
	             .build();

	     response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

	     return ResponseEntity.ok("Logged out successfully");
	 }
	 
	 @GetMapping("/profile")
	 public ResponseEntity<Map<String, Object>> getProfile(Authentication authentication) {

	     Map<String, Object> profile = new HashMap<>();

	     profile.put("username", authentication.getName());

	     String role = authentication.getAuthorities()
	             .stream()
	             .findFirst()
	             .map(a -> a.getAuthority())
	             .orElse("UNKNOWN");

	     profile.put("role", role);

	     return ResponseEntity.ok(profile);
	 }
	 
	 @GetMapping("/api/v1/basedRole")
	 public Map<String, Object> profile(Authentication authentication) {

	     Map<String, Object> response = new HashMap<>();
	     response.put("username", authentication.getName());
	     response.put("roles", authentication.getAuthorities());

	     return response;
	 }
	 
	 
}
