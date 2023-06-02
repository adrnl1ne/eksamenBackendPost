package com.example.eksamenbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.eksamenbackend", "com.example.eksamenbackend.startup"})
@ComponentScan(basePackages = {"com.example.eksamenbackend.Races.repository"})
public class EksamenBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EksamenBackendApplication.class, args);
	}

}
