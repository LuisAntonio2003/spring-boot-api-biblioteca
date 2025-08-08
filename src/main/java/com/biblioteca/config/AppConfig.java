package com.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration 
/* La anotación de configuración contiene la configuracion para crear un beans*/
public class AppConfig {
	
	@Bean
	/* permite crear y devolver un objeto que Spring debe gestionar como un bean */
	public RestTemplate restTemplate() {
		/* Devuelve una instancia de RestTemplate */
		return new RestTemplate();
	}
}
