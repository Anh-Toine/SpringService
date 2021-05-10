package com.nguyen.microservices.core.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.nguyen")
@SpringBootApplication
public class ReviewServiceApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceApplication.class);
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ReviewServiceApplication.class, args);

		String mysqlUrl = ctx.getEnvironment().getProperty("spring.datasource.url");
		LOGGER.info("Connected to MySQL: " + mysqlUrl);
	}

}
