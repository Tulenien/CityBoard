package com.CityBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.CityBoard.models")
@SpringBootApplication
public class CityBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityBoardApplication.class, args);
    }

}
