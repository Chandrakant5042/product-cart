package com.basket.productapi.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	 @GetMapping("/{id}")
	    public ProductResponse getById(@PathVariable Long id) {
	    	System.out.println("api get call");
	        return productService.getProduct(id);
	    }

	 @PostMapping("/login")
	 public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

	     Authentication authentication = authenticationManager.authenticate(
	             new UsernamePasswordAuthenticationToken(
	                     request.username(),
	                     request.password()
	             )
	     );

	     UserDetails userDetails = (UserDetails) authentication.getPrincipal();

	     String accessToken = jwtService.generateAccessToken(userDetails);
	     String refreshToken = jwtService.generateRefreshToken(userDetails);

	     return ResponseEntity.ok(
	             new AuthResponse(accessToken, refreshToken)
	     );
	 }
	 
	 @PostMapping("/refresh")
	 public ResponseEntity<AuthResponse> refreshToken(
	         @RequestBody Map<String, String> request) {

	     String refreshToken = request.get("refreshToken");

	     String username = jwtService.extractUsername(refreshToken);

	     UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	     if (jwtService.isTokenValid(refreshToken, userDetails)) {

	         String newAccessToken = jwtService.generateAccessToken(userDetails);

	         return ResponseEntity.ok(
	                 new AuthResponse(newAccessToken, refreshToken)
	         );
	     }

	     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
	 
	 
}
