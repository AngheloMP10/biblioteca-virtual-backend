package com.biblio.virtual.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import com.biblio.virtual.filter.JwtRequestFilter;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;

	@Autowired
	public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults()) // CORS habilitado para frontend externo
				.csrf(csrf -> csrf.disable()) // API stateless con JWT
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/auth/login", "/auth/register").permitAll()
						.requestMatchers("/auth/me").authenticated()
						.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(
						jwtRequestFilter,
						UsernamePasswordAuthenticationFilter.class); // Autenticación JWT previa al filtro estándar

		return http.build();
	}

	/*
	 * Configuración CORS explícita para controlar orígenes y
	 * evitar exposición global innecesaria.
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(Arrays.asList(
				"http://localhost:4200",
				"https://bucolic-horse-0d3efe.netlify.app"));

		configuration.setAllowedMethods(
				Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/*
	 * BCrypt se usa por ser el estándar recomendado
	 * para almacenamiento seguro de contraseñas.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * Se delega la creación del AuthenticationManager
	 * a la configuración interna de Spring Security.
	 */
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}