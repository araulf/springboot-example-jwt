package com.example.jwt.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.Api;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Api(value = "JWT Auth Request")
@JsonPropertyOrder({ "username", "password" })
public class AuthRequest implements Serializable {
	private static final long serialVersionUID = 3235835940072637749L;

	private String username;
	private String password;
}