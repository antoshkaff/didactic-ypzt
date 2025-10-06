package com.devrezaur.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.devrezaur.main.repository")
@ComponentScan(basePackages = {"com.devrezaur.main.service", "com.devrezaur.main.controller"})
public class SpringBootQuizAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootQuizAppApplication.class, args);
	}

}