package com.jesus.pcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PcServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcServiceApplication.class, args);
	}

}
