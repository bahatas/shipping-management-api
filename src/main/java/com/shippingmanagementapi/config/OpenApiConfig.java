package com.shippingmanagementapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Development server");

        Contact contact = new Contact();
        contact.setName("Shipping Management API");
        contact.setEmail("support@shippingmanagement.com");
        contact.setUrl("https://www.shippingmanagement.com");

        License license = new License()
            .name("MIT License")
            .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
            .title("Shipping Management API")
            .version("1.0.0")
            .contact(contact)
            .description("API for managing shipping operations using ShipEngine")
            .license(license);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer));
    }
} 