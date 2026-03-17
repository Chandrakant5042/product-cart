package com.basket.productapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				// Disable CSRF (because we are using JWT)
				.csrf(csrf -> csrf.disable())

				// Enable CORS
				.cors(cors -> {
				})

				// Stateless session (No HTTP Session)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth

						// ---------------------------
						// ✅ PUBLIC HTML PAGES
						// ---------------------------
						.requestMatchers("/", "/login-page", "/signup-page", "/products-page", "/create-product-page",
								"/edit-product-page", "/error", "/css/**", "/js/**")
						.permitAll()

						// ---------------------------
						// ✅ AUTH APIs (PUBLIC)
						// ---------------------------
						.requestMatchers("/api/v1/login", "/api/v1/signup", "/api/v1/refresh").permitAll()

						// Swagger (optional)
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

						// ---------------------------
						// 🔐 PRODUCT APIs
						// ---------------------------

						// GET products -> USER or ADMIN
						.requestMatchers(HttpMethod.GET, "/api/v1/products/**").hasAnyRole("USER", "ADMIN")

						// POST -> ADMIN
						.requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")

						// PUT -> ADMIN
						.requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")

						// DELETE -> ADMIN
						.requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")

						// Everything else must be authenticated
						.anyRequest().authenticated())

				// Add JWT filter before UsernamePasswordAuthenticationFilter
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// AuthenticationManager Bean (Required for Login)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// Password Encoder Bean
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Proper CORS Configuration
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

		// ⚠ In production, replace "*" with your frontend domain
		configuration.setAllowedOrigins(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}