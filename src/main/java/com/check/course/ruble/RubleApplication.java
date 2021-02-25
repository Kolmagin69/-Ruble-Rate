package com.check.course.ruble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RubleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RubleApplication.class, args);
	}

}
