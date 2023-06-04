package com.nhnacademy.team4.mindooray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class MindoorayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindoorayApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setReadTimeout(Duration.ofSeconds(5L))
				.setConnectTimeout(Duration.ofSeconds(5L))
				.build();
	}
}
