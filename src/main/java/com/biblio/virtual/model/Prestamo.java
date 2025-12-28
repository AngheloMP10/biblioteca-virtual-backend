package com.biblio.virtual.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import com.biblio.virtual.model.enums.EstadoPrestamo;

@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fecha_solicitud", nullable = false)
	private LocalDate fechaSolicitud;

	@Column(name = "fecha_recojo")
	private LocalDate fechaRecojo;

	@Column(name = "fecha_devolucion")
	private LocalDate fechaDevolucion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoPrestamo estado;

	@Column(name = "qr_code", unique = true)
	private String qrCode;

	// Relaciones
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "libro_id", nullable = false)
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

	public LocalDate getFechaRecojo() {
		return fechaRecojo;
	}

	public void setFechaRecojo(LocalDate fechaRecojo) {
		this.fechaRecojo = fechaRecojo;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public EstadoPrestamo getEstado() {
		return estado;
	}

	public void setEstado(EstadoPrestamo estado) {
		this.estado = estado;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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
