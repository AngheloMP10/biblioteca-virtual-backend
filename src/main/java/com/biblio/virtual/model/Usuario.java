package com.biblio.virtual.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String email;

	@Column(length = 20)
	private String celular;

	// String para mantener compatibilidad con Spring Security
	@Column(nullable = false)
	private String role;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Prestamo> prestamos = new ArrayList<>();

	public Usuario() {
	}

	public Usuario(String username, String password, String role, String email, String celular) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
		this.celular = celular;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
}