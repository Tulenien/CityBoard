package com.CityBoard.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.CityBoard.postgresql.repository")
@EntityScan(basePackages = "com.CityBoard.postgresql.dto")
@SpringBootApplication(scanBasePackages = {"com.CityBoard.application",
        "com.CityBoard.models",
        //"com.CityBoard.controllers",
        "com.CityBoard.rest",
        "com.CityBoard.services",
        "com.CityBoard.ui"})
public class CityBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityBoardApplication.class, args);
    }
}
