package com.biblio.virtual.filter;

import com.biblio.virtual.service.CustomUserDetailsService;
import com.biblio.virtual.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		// Log para debug: verificar si llega el header
		if (authorizationHeader == null) {
			System.out.println(">>> JWT FILTER: No hay header Authorization");
		}

		String username = null;
		String jwt = null;

		// Extraer token Bearer
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			try {
				username = jwtUtil.extractUsername(jwt); // Extrae username del JWT
				System.out.println(">>> JWT FILTER: Usuario extraído del token: " + username);
			} catch (Exception e) {
				System.out.println(">>> JWT FILTER ERROR: Token inválido/malformado: " + e.getMessage());
			}
		}

		// Si hay usuario y no está autenticado aún
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// Cargar usuario desde BD
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			System.out.println(">>> JWT FILTER: Usuario encontrado en BD: " + userDetails.getUsername());

			// Validar token
			boolean isValid = jwtUtil.validateToken(jwt, userDetails.getUsername());

			if (isValid) {
				// Extraer y limpiar rol
				String role = jwtUtil.extractRole(jwt);
				if (role != null)
					role = role.trim();
				if (role != null && !role.startsWith("ROLE_"))
					role = "ROLE_" + role;
				if (role != null)
					role = role.trim();

				System.out.println(">>> JWT FILTER: Rol final asignado: '" + role + "'");

				// Crear token de autenticación con rol
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, List.of(new SimpleGrantedAuthority(role)));

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Establecer autenticación en el contexto de seguridad
				SecurityContextHolder.getContext().setAuthentication(authToken);
				System.out.println(">>> JWT FILTER: ¡Autenticación establecida en el Contexto!");
			} else {
				System.out.println(">>> JWT FILTER ERROR: validateToken retornó FALSE.");
			}
		}

		// Continuar con el filtro
		chain.doFilter(request, response);
	}
}