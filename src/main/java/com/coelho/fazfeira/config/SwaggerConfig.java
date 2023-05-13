package com.coelho.fazfeira.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")

                                .in(SecurityScheme.In.HEADER)
                                .name(HttpHeaders.AUTHORIZATION)))

                .info(new Info()
                        .title("iDelivery")
                        .version("1.0.0")
                        .description("APIs para back-end do iDeliver"))
                .servers(Arrays.asList(
                        new Server().url(serverUrl)));
    }


}

