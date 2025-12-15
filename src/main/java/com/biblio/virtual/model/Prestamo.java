package com.biblio.virtual.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_solicitud", nullable = false)
	private LocalDate fechaSolicitud;

	@Column(name = "fecha_devolucion")
	private LocalDate fechaDevolucion;

	/*
	 * El estado se modela como String para mantener flexibilidad
	 * y evitar acoplar la persistencia a un enum cerrado.
	 */
	@Column(nullable = false)
	private String estado;

	/*
	 * Relación con Usuario cargada de forma EAGER para que los
	 * datos necesarios del solicitante estén disponibles
	 * sin consultas adicionales.
	 * Se ocultan campos sensibles y relaciones inversas.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	@JsonIgnoreProperties({ "prestamos", "password", "role" })
	private Usuario usuario;

	/*
	 * Relación con Libro cargada de forma inmediata para permitir
	 * mostrar información básica del libro en listados de préstamos,
	 * evitando accesos diferidos fuera de sesión.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "libro_id", nullable = false)
	@JsonIgnoreProperties("prestamos")
	private Libro libro;

	public Prestamo() {
	}

	// Getters y setters
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}
}