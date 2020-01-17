package com.example.jwt.model;

import java.io.Serializable;

import io.swagger.annotations.Api;
import lombok.Getter;

@Api(value = "JWT Auth Response")
public class AuthResponse implements Serializable {

	private static final long serialVersionUID = -4632795351942661915L;
	
	@Getter
	private final String jwt;

	public AuthResponse(String jwt) {
		this.jwt = jwt;
	}
}