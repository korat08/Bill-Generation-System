package com.example.Bill_Generation_System;

import com.example.Bill_Generation_System.DTO.TwilioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TwilioConfig.class)
public class BillGenerationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillGenerationSystemApplication.class, args);
	}

}
