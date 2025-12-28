package com.biblio.virtual;

import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.UsuarioRepository;
import com.biblio.virtual.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BibliotecaVirtualApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaVirtualApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData() {
		return args -> {

			if (usuarioRepository.findByUsername("admin").isEmpty()) {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole(Roles.ADMIN);
				usuarioRepository.save(admin);
				System.out.println("Usuario ADMIN creado: admin / admin123");
			}

			if (usuarioRepository.findByUsername("bibliotecario").isEmpty()) {
				Usuario bibliotecario = new Usuario();
				bibliotecario.setUsername("bibliotecario");
				bibliotecario.setPassword(passwordEncoder.encode("bibliotecario123"));
				bibliotecario.setRole(Roles.BIBLIOTECARIO);
				usuarioRepository.save(bibliotecario);
				System.out.println("Usuario BIBLIOTECARIO creado: bibliotecario / bibliotecario123");
			}

			if (usuarioRepository.findByUsername("user").isEmpty()) {
				Usuario user = new Usuario();
				user.setUsername("user");
				user.setPassword(passwordEncoder.encode("user123"));
				user.setRole(Roles.USER);
				usuarioRepository.save(user);
				System.out.println("Usuario USER creado: user / user123");
			}
		};
	}
}
