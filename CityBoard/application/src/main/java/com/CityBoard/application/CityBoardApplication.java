package com.CityBoard.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.CityBoard.repositories")
@EntityScan(basePackages = "com.CityBoard.models")
@ComponentScan(basePackages = "com.CityBoard")
@SpringBootApplication
public class CityBoardApplication {
public static void main(String[] args) {
        SpringApplication.run(CityBoardApplication.class, args);
    }
}
