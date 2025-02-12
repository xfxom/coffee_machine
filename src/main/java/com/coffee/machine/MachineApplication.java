package com.coffee.machine;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Coffee Machine API",
				version = "1.0.0",
				description = "API for Coffee Machine"
		)
)
@SpringBootApplication
public class MachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachineApplication.class, args);
	}

}
