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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value = "JWT Secured Welcome Controller")
public class JwtSecuredController {

	private static final Logger logger = LogManager.getLogger(JwtSecuredController.class);

	@ApiOperation(value = "JWT Secured Welcome Page", response = String.class)
	@RequestMapping(value = "/jwt/welcome", method = RequestMethod.GET)
	public String welcome(@RequestHeader(value = "Authorization", required = true) String authorization) {
		logger.info("Executing...");
		return "Hello, Welcome to JWT Authenticated Page";
	}
}