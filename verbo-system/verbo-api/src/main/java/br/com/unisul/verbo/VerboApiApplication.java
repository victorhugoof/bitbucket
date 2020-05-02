package br.com.unisul.verbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VerboApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerboApiApplication.class, args);
	}

}
