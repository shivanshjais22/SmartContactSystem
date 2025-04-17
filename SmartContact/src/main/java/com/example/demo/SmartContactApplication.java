package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "com.example.User", "com.repo"}) // include everything
@EntityScan(basePackages = "com.example.User")
@EnableJpaRepositories(basePackages = "com.repo")
public class SmartContactApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactApplication.class, args);
		
	}
}
