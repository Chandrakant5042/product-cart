package com.basket.productapi.service;

import com.basket.productapi.dto.LoginRequest;
import com.basket.productapi.dto.LoginResponse;
import com.basket.productapi.dto.RegisterRequest;
import com.basket.productapi.entity.User;
import com.basket.productapi.repository.UserRepository;
import com.basket.productapi.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private ResponseCookie buildCookie(String name, String value, long maxAgeSeconds, boolean httpOnly,
			String sameSite) {
		return ResponseCookie.from(name, value).httpOnly(httpOnly).secure(false).path("/")
				.maxAge(Duration.ofSeconds(maxAgeSeconds)).sameSite(sameSite).build();
	}

	@Override
	public ResponseEntity<LoginResponse> login(LoginRequest request, HttpServletResponse response) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		String accessToken = jwtService.generateAccessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		response.addHeader(HttpHeaders.SET_COOKIE,
				buildCookie("accessToken", accessToken, 900, true, "Strict").toString());

		response.addHeader(HttpHeaders.SET_COOKIE,
				buildCookie("refreshToken", refreshToken, 604800, true, "Strict").toString());

		return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
	}

	@Override
	public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {

		Optional<String> refreshTokenOpt = Arrays
				.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
				.filter(c -> "refreshToken".equals(c.getName())).map(Cookie::getValue).findFirst();

		if (refreshTokenOpt.isEmpty())
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		String username = jwtService.extractUsername(refreshTokenOpt.get());
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		if (!jwtService.isTokenValid(refreshTokenOpt.get(), userDetails))
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		String newAccessToken = jwtService.generateAccessToken(userDetails);

		response.addHeader(HttpHeaders.SET_COOKIE,
				buildCookie("accessToken", newAccessToken, 900, true, "Lax").toString());

		return ResponseEntity.ok(new LoginResponse(newAccessToken, refreshTokenOpt.get()));
	}

	@Override
	public ResponseEntity<LoginResponse> signup(RegisterRequest request) {

		if (userRepository.findByUsername(request.username()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

		User user = new User();
		user.setUsername(request.username());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRole(request.role() == null ? "USER" : request.role().toUpperCase());

		userRepository.save(user);

		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		String accessToken = jwtService.generateAccessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
	}

	@Override
	public ResponseEntity<?> logout(HttpServletResponse response) {

		response.addHeader(HttpHeaders.SET_COOKIE, buildCookie("accessToken", "", 0, true, "Strict").toString());

		response.addHeader(HttpHeaders.SET_COOKIE, buildCookie("refreshToken", "", 0, true, "Strict").toString());

		return ResponseEntity.ok("Logged out successfully");
	}

	@Override
	public ResponseEntity<Map<String, Object>> getProfile(Authentication authentication) {

		return ResponseEntity.ok(Map.of("username", authentication.getName(), "role",
				authentication.getAuthorities().stream().findFirst().map(a -> a.getAuthority()).orElse("UNKNOWN")));
	}
}