package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotesApplication.class, args);
	}

}
