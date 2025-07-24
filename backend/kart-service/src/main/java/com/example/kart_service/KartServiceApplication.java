package com.example.kart_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KartServiceApplication.class, args);
	}

}
