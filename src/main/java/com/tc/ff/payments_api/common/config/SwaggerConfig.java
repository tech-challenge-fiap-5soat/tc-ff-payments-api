package com.tc.ff.payments_api.common.config;

import static java.util.stream.Stream.of;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "tech challenge fast food paymentys API",
                        description = "service that manages the payments flow",
                        version = "0.0.1"),
        externalDocs = @ExternalDocumentation(description = "Find out more about Swagger", url = "http://swagger.io"))
@Configuration
public class SwaggerConfig {

    @Value("${swagger-servers-urls}")
    private String[] swaggerServersUrls;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();
        of(swaggerServersUrls).forEach(url -> openAPI.addServersItem(new Server().url(url)));
        return openAPI;
    }
}
