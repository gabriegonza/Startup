package br.com.satrtup.Startup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
	
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("RESTful API with Java 17 and Spring Boot v3.2.0")
				.version("v1")
				.description("Some description about your API")
				.termsOfService("https://startupgabriel.com.br/meus-cursos")
				.license(
					new License()
						.name("Apache 2.0")
						.url("https://startupgabriel.com.br/meus-cursos")
					)
				);
	}

}
