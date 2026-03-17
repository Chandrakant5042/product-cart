package com.basket.productapi.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
    private JwtServiceImpl jwtServiceImpl;
	@Autowired
    private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
	        @NonNull HttpServletRequest request,
	        @NonNull HttpServletResponse response,
	        @NonNull FilterChain filterChain)
	        throws ServletException, IOException {

	    final String authHeader = request.getHeader("Authorization");

	    // 1️⃣ If no header or wrong prefix → skip filter
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    final String jwt = authHeader.substring(7);

	    // 2️⃣ Extra safety check
	    if (jwt == null || jwt.isBlank() || !jwt.contains(".")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    try {
	        // 3️⃣ Extract username safely
	        String username = jwtServiceImpl.extractUsername(jwt);

	        // 4️⃣ Set authentication if valid
	        if (username != null &&
	                SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails =
	                    userDetailsService.loadUserByUsername(username);

	            if (jwtServiceImpl.isTokenValid(jwt, userDetails)) {

	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(
	                                userDetails,
	                                null,
	                                userDetails.getAuthorities()
	                        );

	                authToken.setDetails(
	                        new WebAuthenticationDetailsSource()
	                                .buildDetails(request)
	                );

	                SecurityContextHolder.getContext()
	                        .setAuthentication(authToken);
	            }
	        }

	    } catch (Exception ex) {
	        // 🔐 IMPORTANT: Do NOT break request pipeline
	        // Just log and continue
	        System.out.println("Invalid JWT: " + ex.getMessage());
	    }

	    filterChain.doFilter(request, response);
	}
}
