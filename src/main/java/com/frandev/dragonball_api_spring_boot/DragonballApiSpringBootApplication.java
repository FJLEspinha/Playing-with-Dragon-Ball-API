package com.frandev.dragonball_api_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DragonballApiSpringBootApplication {

	@Bean
	public RestTemplate getresttemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(DragonballApiSpringBootApplication.class, args);
	}

}
