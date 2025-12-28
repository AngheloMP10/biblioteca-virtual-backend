package com.biblio.virtual.security;

import org.springframework.stereotype.Component;

@Component("roles")
public class RoleExpressions {

	public String USER() {
		return Roles.USER;
	}

	public String BIBLIOTECARIO() {
		return Roles.BIBLIOTECARIO;
	}

	public String ADMIN() {
		return Roles.ADMIN;
	}
}
