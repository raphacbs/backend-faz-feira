package com.coelho.fazfeira;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FazFeiraApplication {

	public static void main(String[] args) {
		SpringApplication. run(FazFeiraApplication.class, args);
	}

}
