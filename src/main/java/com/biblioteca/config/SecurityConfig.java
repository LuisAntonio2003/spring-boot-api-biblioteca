package com.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration // permite la configuración de un beans
@EnableWebSecurity // Habilitaba la integración de Spring Security en la aplicación
public class SecurityConfig {
	@Bean // Permite crear un bean para el filtro de seuridad 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
				.httpBasic(basic -> {
				}); // HABILITA LA AUTENTICACIÓN BÁSICA
		return http.build();
	}

	@Bean // Permite crear un bean para el servicio de detalles del usuario
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("biblioteca"))
																											
				.roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean // Permite crear un bean para el codificador de contraseña
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
