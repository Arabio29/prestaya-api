package com.example.prestamos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Prestamos", version = "1.0", description = "API para prestamistas."))
public class PrestamosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrestamosApplication.class, args);
	}

}
