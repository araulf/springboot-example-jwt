/**
 * Copyright NEWFOUND SYSTEMS to Present
 * All Rights Reserved
 */
package com.example.jwt.bcrypt.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.common.collect.Lists;

public class GenerateBCryptPassword {

	static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^*()-_[{]}|:,.";

	static final int USER_LEN = 30;
	static final int PASSWORD_LEN = 20;

	private static List<String> apiUsers = null;
	BCryptPasswordEncoder encoder = null;

	/**
	 * Constructor
	 */
	public GenerateBCryptPassword() {
		encoder = new BCryptPasswordEncoder();
		apiUsers = getComplexUsers();
	}

	/**
	 * Generate Simple Password
	 */
	void createSimplePass() {
		System.out.println("\nSimple Password---");
		apiUsers = Lists.newArrayList("foo");
		for (String user : apiUsers) {
			String rawPassword = user.toLowerCase();
			String encoded = encoder.encode(rawPassword);
			System.out.printf("User [%s] rawPassword [%s] encoded [%s]\n", user, rawPassword, encoded);
		}
	}

	/**
	 * Generate Medium Complex Password
	 */
	void createMediumPass() {
		System.out.println("\nMedium Password---");
		for (String user : apiUsers) {
			String rawPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LEN);
			String encoded = encoder.encode(rawPassword);
			System.out.printf("User [%s] rawPassword [%s] encoded [%s]\n", user, rawPassword, encoded);
		}
	}

	/**
	 * Generate Complex Password
	 */
	void createComplexPass() {
		System.out.println("\nComplex Password---");
		for (String user : apiUsers) {
			String rawPassword = RandomStringUtils.random(PASSWORD_LEN, CHARS);
			String encoded = encoder.encode(rawPassword);
			System.out.printf("User [%s] rawPassword [%s] encoded [%s]\n", user, rawPassword, encoded);
		}
	}

	/**
	 * Create User(s)
	 */
	List<String> getComplexUsers() {
		List<String> apiUsers = Lists.newArrayList();
		System.out.println("\nUser(s)---");
		for (String user : apiUsers) {
			String rawUserId = user + "_" + RandomStringUtils.randomAlphanumeric(USER_LEN);
			String encoded = encoder.encode(rawUserId);
			System.out.printf("User [%s] rawUserId [%s] encoded [%s]\n", user, rawUserId, encoded);
			apiUsers.add(rawUserId);
		}
		return apiUsers;
	}
	
	/**
	 * Generate Credentials
	 */
	void generateCredentials() {
		createSimplePass();
		createMediumPass();
		createComplexPass();
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GenerateBCryptPassword service = new GenerateBCryptPassword();
		service.generateCredentials();
	}
}