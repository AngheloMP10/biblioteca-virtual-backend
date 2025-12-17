package com.biblio.virtual.dto;

import java.time.LocalDate;

public class PrestamoDTO {

	private Long id;
	private LocalDate fechaSolicitud;
	private LocalDate fechaDevolucion;
	private String estado;

	// DTOs anidados
	private UsuarioDTO usuario;
	private LibroDTO libro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public LibroDTO getLibro() {
		return libro;
	}

	public void setLibro(LibroDTO libro) {
		this.libro = libro;
	}
}
