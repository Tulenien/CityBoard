package com.CityBoard.application.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI OpenAPIConfiguration() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8080").description("API Server"));

        return new OpenAPI()
                .info(new Info().title("CityBoard API").description("Realty operations")
                        .license(new License().url("http://mylicense.com").name("License info"))
                        .contact(new Contact().name("Timofey Evsigneev")
                                .email("timofey.evsigneev@gmail.com"))
                        .version("1.0.0"))
                .servers(servers);
    }
}