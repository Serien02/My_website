package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.JsonProcessingException;


@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(DemoApplication.class, args);
	}

}

