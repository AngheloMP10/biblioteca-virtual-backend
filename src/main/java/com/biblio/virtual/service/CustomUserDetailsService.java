package com.biblio.virtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Buscar usuario en la base de datos
		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		// Asegurar que el rol tenga el prefijo ROLE_ (requisito Spring Security)
		String role = usuario.getRole().startsWith("ROLE_") ? usuario.getRole() : "ROLE_" + usuario.getRole();

		// Construir UserDetails con username, password y rol
		return User.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(role)
				.build();
	}

}