package com.nhnacademy.team4.mindooray;

import com.nhnacademy.team4.mindooray.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.ClientHttpRequestFactorySupplier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;

@SpringBootApplication
@RequiredArgsConstructor
public class MindoorayApplication {
	private final ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(MindoorayApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.requestFactory(HttpComponentsClientHttpRequestFactory::new)
				.setReadTimeout(Duration.ofSeconds(5L))
				.setConnectTimeout(Duration.ofSeconds(5L))
				.build();
	}

	@PostConstruct
	public void initBeanUtils() {
		BeanUtils.init(applicationContext);
	}
}
