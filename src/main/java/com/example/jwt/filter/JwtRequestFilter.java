package com.example.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwt.service.JwtUserDetailsService;
import com.example.jwt.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final Logger logger = LogManager.getLogger(JwtRequestFilter.class);

	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		final String reqAuthTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;

		if (reqAuthTokenHeader != null && reqAuthTokenHeader.startsWith("Bearer ")) {
			jwt = reqAuthTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwt);
			} catch (IllegalArgumentException e) {
				logger.info("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				logger.info("JWT Token has expired");
			}
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
			/**
			 * Validate Token
			 */
			if (jwtTokenUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				userAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userAuth);
			}
		}
		chain.doFilter(request, response);
	}
}