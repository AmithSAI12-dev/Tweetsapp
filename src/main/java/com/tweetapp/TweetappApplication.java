package com.tweetapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition
@SpringBootApplication()
public class TweetappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetappApplication.class, args);
	}
}
