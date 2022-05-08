package com.example.freezer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.freezer.model")
public class FreezerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreezerApplication.class, args);
	}

}
