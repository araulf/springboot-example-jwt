package com.example.jwt.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -1491718022965232652L;
	
	private static final Logger logger = LogManager.getLogger(JwtTokenUtil.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.token.expiry}")
	private Long jstTokenExpiry;

	/**
	 * Get Username
	 * 
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extract Expiration
	 * 
	 * @param token
	 * @return
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extract Claim
	 * 
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extract All Claims
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

	/**
	 * Verify if Token Expired
	 * 
	 * @param token
	 * @return
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Generate Token
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String jwtToken = createToken(claims, userDetails.getUsername());
		logger.info("Creating Jwt for username: " + userDetails.getUsername());
		return jwtToken;
	}

	/**
	 * Create Token
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jstTokenExpiry * 1000))
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
	}

	/**
	 * Validate Token
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}