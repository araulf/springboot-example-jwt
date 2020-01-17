/**
 * <pre><b>NEWFOUND SYSTEMS</b> Proprietary/Confidential. 
 * Copyright (c) 2009 till present with NEWFOUND.
 * Usage is subject to terms and conditions between <b>NEWFOUND and DHL Express</b>
 * 
 * @project		springboot-example-jwt
 * @author		User
 * @date		Jan 16, 2020
 */
/**
 * 
 */
package com.example.jwt.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.model.AuthRequest;
import com.example.jwt.model.AuthResponse;
import com.example.jwt.service.JwtUserDetailsService;
import com.example.jwt.util.JwtTokenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value = "JWT Authentication Controller")
public class JwtAuthController {

	private static final Logger logger = LogManager.getLogger(JwtAuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	JwtUserDetailsService jwtUserDetailsService;

	/**
	 * Authenticate to get JWT
	 * 
	 * @param authRequest
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Authenticate to get JWT", response = AuthResponse.class)
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest authRequest) throws Exception {
		logger.info("Executing...");
		try {
			authenticate(authRequest.getUsername(), authRequest.getPassword());
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid username or password", e);
		}
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
		logger.info(userDetails);

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(jwt));
	}

	/**
	 * Authenticate
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {
		logger.info("Authenticating username: " + username);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}