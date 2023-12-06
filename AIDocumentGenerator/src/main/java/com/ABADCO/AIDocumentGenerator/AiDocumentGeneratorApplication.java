package com.ABADCO.AIDocumentGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
        )
public class AiDocumentGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiDocumentGeneratorApplication.class, args);
	}

}
