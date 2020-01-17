/**
 * Copyright NEWFOUND SYSTEMS to Present
 * All Rights Reserved
 */
package com.example.jwt.bcrypt.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MatchBCryptPassword {

	static final String encodedPassword = "$2a$10$wWWNjnT9k6pXYqwu/xpmiumnOjGAk0PzJzBRefc7mZUPr.HRNViaC";

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "foo";
		System.out.println("Password Match: " + encoder.matches(rawPassword, encodedPassword));
	}
}