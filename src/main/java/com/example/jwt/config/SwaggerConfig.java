/**
 * Copyright NEWFOUND SYSTEMS to Present
 * All Rights Reserved
 */
package com.example.jwt.config;

import static com.google.common.base.Predicates.or;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	private static final Logger logger = LogManager.getLogger(SwaggerConfig.class);
	
	@Bean
	public Docket api() {
		logger.info("Configuring Swagger for " + DocumentationType.SWAGGER_2);
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(selectedPaths())
				.build()
				.groupName("public-jwt")
				.produces(Sets.newHashSet("application/json"))
				.consumes(Sets.newHashSet("application/json"))
				.apiInfo(apiInfo());
	}
	
	@Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("Configuring Resource Handler(s)...");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	/**
	 * Predicate Paths
	 * 
	 * @return
	 */
	private Predicate<String> selectedPaths() {
		return or(PathSelectors.regex("/jwt.*"), PathSelectors.regex("/authenticate.*"));
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
				.clientId("user")
				.clientSecret("password")
				.scopeSeparator(" ")
				.useBasicAuthenticationWithAccessCodeGrant(true)
				.build();
	}

	/**
	 * API Documentation
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("Developer", "http://www.newfound-systems.com", "support@newfound-systems.com");
		return new ApiInfoBuilder()
				.title("SpringBoot JWT")
				.description("SpringBoot JWT")
				.termsOfServiceUrl("http://www.newfound-systems.com")
				.contact(contact)
				.license("Open")
				.licenseUrl("http://www.newfound-systems.com")
				.version("1.0.0")
				.build();
	}
}