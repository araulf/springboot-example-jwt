/**
 * Copyright NEWFOUND SYSTEMS to Present
 * All Rights Reserved
 */
/**
 * 
 */
package com.example.jwt.config;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(
        info = @Info(
                description = "DHL Api Services",
                version = "v1.0.0",
                title = "DHL Api",
                contact = @Contact(
                   name = "NEWFOUND SYSTEMS", 
                   email = "support@newfound-systems.com", 
                   url = "http://www.newfound-systems.com"
                ),
                license = @License(
                   name = "DHL Express", 
                   url = "http://www.dhl.com"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTPS},
        externalDocs = @ExternalDocs(value = "Read this", url = "http://www.dhl.com")
)
public interface ApiDocumentationConfig {
}