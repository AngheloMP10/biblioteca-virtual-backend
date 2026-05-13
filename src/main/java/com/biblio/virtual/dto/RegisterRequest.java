package com.biblio.virtual.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

	@NotBlank(message = "Username obligatorio")
	private String username;

	@NotBlank(message = "Password obligatoria")
	@Size(min = 8, max = 20)
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).+$", message = "Debe tener mayúscula, número y carácter especial")
	private String password;

	@NotBlank
	@Email
	private String email;

	@Pattern(regexp = "^\\d{9}$", message = "Debe tener 9 dígitos")
	private String celular;

	public RegisterRequest() {
	}

	public RegisterRequest(String username, String password, String email, String celular) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.celular = celular;
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

}
