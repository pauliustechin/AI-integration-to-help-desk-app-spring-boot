package com.psem.springBootWithOllama.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Create OpenAPI bean to customize swagger documentation.
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot with Ollama")
                        .description("Integrate AI to handle customer's comments")
                        .contact(new Contact()
                                .name("Paulius Semaska")
                                .email("pauliustechin@gmail.com")
                                .url("https://github.com/pauliustechin")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project on git hub")
                        .url("https://github.com/pauliustechin/AI-integration-to-help-desk-app-spring-boot.git"));
    }
}

