/**
 * <pre><b>NEWFOUND SYSTEMS</b> Proprietary/Confidential. 
 * Copyright (c) 2009 till present with NEWFOUND.
 * Usage is subject to terms and conditions between <b>NEWFOUND and DHL Express</b>
 * 
 * @project		springboot-example-jwt
 * @author		User
 * @date		Jan 16, 2020
 */
package com.example.jwt.service;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 8072979362483405403L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}