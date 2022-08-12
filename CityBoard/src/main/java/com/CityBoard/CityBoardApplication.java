package com.CityBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.CityBoard.models")
@EnableJpaRepositories(basePackages = "com.CityBoard.repositories")
@SpringBootApplication
public class CityBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CityBoardApplication.class, args);
	}

}
