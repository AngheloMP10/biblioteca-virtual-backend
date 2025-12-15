package com.biblio.virtual.dto;

public class AuthResponse {

	private String token; // JWT generado para el usuario
	private String tipoToken = "Bearer"; // Tipo de token esperado por el frontend
	private String username; // Nombre de usuario autenticado
	private String role; // Rol del usuario ("ROLE_USER" o "ROLE_ADMIN")

	public AuthResponse(String token, String username, String role) {
		this.token = token;
		this.username = username;
		this.role = role;
	}

	// Getters y setters necesarios para serialización/deserialización
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTipoToken() {
		return tipoToken;
	}

	public void setTipoToken(String tipoToken) {
		this.tipoToken = tipoToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}