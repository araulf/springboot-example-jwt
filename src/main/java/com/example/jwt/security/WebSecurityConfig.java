/**
 * <pre><b>NEWFOUND SYSTEMS</b> Proprietary/Confidential. 
 * Copyright (c) 2009 till present with NEWFOUND.
 * Usage is subject to terms and conditions between <b>NEWFOUND and DHL Express</b>
 * 
 * @project		springboot-example-jwt
 * @author		User
 * @date		Jan 16, 2020
 */
package com.example.jwt.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.jwt.filter.JwtRequestFilter;
import com.example.jwt.service.JwtAuthEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	private static final Logger logger = LogManager.getLogger(WebSecurityConfig.class);

	@Autowired
	JwtAuthEntryPoint jwtAuthEntryPoint;

	@Autowired
	UserDetailsService jwtUserDetailsService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		logger.info("Configuring ignore web path(s)...");
		web.ignoring()
			.antMatchers("/v2/api-docs", 
							"/configuration/ui", 
							"/swagger-resources/**",
							"/configuration/security", 
							"/swagger-ui.html", 
							"/webjars/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bcryptPasswordEncoder());
	}

	@Bean
	public PasswordEncoder bcryptPasswordEncoder() {
		logger.info("Configuring BCrypt Password Encoder...");
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		logger.info("Configuring httpSecurity...");
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
					.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		/**
		 * Validate Token for each request
		 */
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}