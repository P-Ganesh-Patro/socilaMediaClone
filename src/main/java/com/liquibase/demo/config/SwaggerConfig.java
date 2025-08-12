package com.liquibase.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Info apiInfo = new Info()
                .title("Instagram Backend APIs")
                .description("By Ganesh")
                .version("1.0");

        List<Server> servers = List.of(
                new Server().url("http://localhost:8080").description("Local environment")
        );

        List<Tag> tags = Arrays.asList(
                new Tag().name("User Authentication").description("Handles user signup and login operations"),
                new Tag().name("Post Comments").description("APIs for managing comments on posts"),
                new Tag().name("Group").description("Handles group creation and management"),
                new Tag().name("Group Members").description("APIs to manage group members"),
                new Tag().name("Group Posts").description("Operations for group posts"),
                new Tag().name("Post Files").description("Upload and manage post-related files"),
                new Tag().name("Reaction on Post").description("Managing reactions on post"),
                new Tag().name("User APIs").description("update, delete & fetch- users"),
                new Tag().name("Reaction Types"),
                new Tag().name("Group Role")

        );

        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new OpenAPI()
                .info(apiInfo)
                .servers(servers)
                .tags(tags)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth", bearerAuthScheme));
    }
}
