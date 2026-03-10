package com.basket.productapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtServiceImpl jwtServiceImpl;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		extractTokenFromCookie(request, "accessToken").ifPresent(jwt -> {
			try {
				String username = jwtServiceImpl.extractUsername(jwt);

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					if (jwtServiceImpl.isTokenValid(jwt, userDetails)) {
						var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
								userDetails.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}
			} catch (Exception ex) {
				System.out.println("[JWT Filter] Invalid token: " + ex.getMessage());
			}
		});

		filterChain.doFilter(request, response);
	}

	private Optional<String> extractTokenFromCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() == null)
			return Optional.empty();
		return Arrays.stream(request.getCookies()).filter(c -> cookieName.equals(c.getName())).map(Cookie::getValue)
				.findFirst();
	}
}