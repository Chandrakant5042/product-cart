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
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable())
                // ❗ Still stateless (we are using JWT, not session)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // CSRF disabled (acceptable for stateless JWT)
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(cors -> {})

                .authorizeHttpRequests(auth -> auth

                        // Public HTML Pages
                		.requestMatchers("/", "/login-page", "/signup-page",
                		        "/products",   // ✅ ADD THIS
                		        "/create-product",
                		        "/edit-product",
                		        "/products",
                		        "/my-products",
                		        "/error", "/css/**", "/js/**", "/favicon.ico",
                		        "/.well-known/**")
                		.permitAll()

                        // Public APIs
                        .requestMatchers("/api/v1/login",
                                "/api/v1/signup",
                                "/api/v1/refresh")
                        .permitAll()

                        // Swagger
                        .requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**")
                        .permitAll()

                        // 🔐 Product APIs
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**")
                        .hasAnyRole("USER", "ADMIN")

                    .requestMatchers(HttpMethod.POST, "/api/v1/products/**")
                        .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.PUT, "/api/v1/products/**")
                        .hasRole("ADMIN")

                    .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**")
                        .hasRole("ADMIN")
                        
                     // ✅ Purchases
                        .requestMatchers(HttpMethod.POST, "/api/v1/purchases/**")
                        .hasRole("USER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/purchases/**")
                        .hasRole("USER")
                        
                     // 👤 User Profile
                        .requestMatchers(HttpMethod.GET, "/api/v1/profile")
                        .hasAnyRole("USER","ADMIN")

                        .anyRequest().authenticated()
                )

                // Add JWT filter
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // AuthenticationManager Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ IMPORTANT: CORS CONFIG FOR COOKIE AUTH
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // ⚠ DO NOT USE "*" when using cookies
        configuration.setAllowedOrigins(
                List.of("http://localhost:8080")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(List.of("*"));

        // ✅ MUST be true for cookies
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}