package com.CityBoard.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CityBoardApplication.class);
    public static void main(String[] args) {
        logger.trace("Start Cityboard application with args {}", args);
        SpringApplication.run(CityBoardApplication.class, args);
        logger.trace("Stop Cityboard application");
    }
}
