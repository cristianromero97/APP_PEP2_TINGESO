package com.example.discount_visit_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscountVisitServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountVisitServiceApplication.class, args);
	}

}
