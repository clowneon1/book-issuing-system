package com.clowneon1.bookissuingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BookIssuingSystemApplication {

	//Start Api
	public static void main(String[] args) {
		SpringApplication.run(BookIssuingSystemApplication.class, args);
	}

}
