package com.example.subscription.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/** Configures API metadata and the JWT bearer scheme shown in Swagger UI. */
public class OpenApiConfig {

    @Bean
    OpenAPI subscriptionApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Subscription Management API")
                        .version("v1")
                        .description("Manage subscription plans and billing cycles."))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
