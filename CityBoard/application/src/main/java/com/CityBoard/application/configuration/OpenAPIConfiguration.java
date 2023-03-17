package com.CityBoard.application.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class OpenAPIConfiguration {
    @Value("${server.port}")
    private String port;

    static {
        io.swagger.v3.core.jackson.ModelResolver.enumsAsRef = true;
    }

    @Bean
    public OpenAPI OpenAPIConfiguration() {
        String baseUrl = "http://localhost:" + port;
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url(baseUrl).description("Instance"));
        servers.add(new Server().url("http://176.118.165.63:20078").description("Nginx proxy"));
        servers.add(new Server().url("http://176.118.165.63:20078/api/v2").description("Swagger"));
        servers.add(new Server().url("http://176.118.165.63:20078/api/v1").description("API"));
        //servers.add(new Server().url("http://localhost:10077").description("Main API Server"));
        //servers.add(new Server().url("http://localhost:10078").description("Test Server"));
        //servers.add(new Server().url("http://localhost:10177").description("API Server, ReadOnly"));
        //servers.add(new Server().url("http://localhost:10178").description("API Server, ReadOnly"));
        //servers.add(new Server().url("http://localhost:10277/legacy").description("Legacy"));
        //servers.add(new Server().url("http://localhost:10377/mirror1").description("Mirror"));
        //servers.add(new Server().url("http://cityboard/api/v2").description("Swagger"));
        //servers.add(new Server().url("http://cityboard/api/v1").description("API"));

        return new OpenAPI()
                .info(new Info().title("CityBoard API").description("Realty operations")
                        .license(new License().url("http://mylicense.com").name("License info"))
                        .contact(new Contact().name("Timofey Evsigneev")
                                .email("timofey.evsigneev@gmail.com"))
                        .version("1.0.0"))
                .servers(servers);
    }
}