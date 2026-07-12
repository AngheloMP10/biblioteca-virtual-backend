package com.biblio.virtual.model;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "autores")
public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "El nombre del autor no debe estar vacío")
	private String nombre;

	@Column(name = "url_foto")
	private String urlFoto;

	// Relación inversa ManyToMany
	@ManyToMany(mappedBy = "autores", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Libro> libros = new HashSet<>();

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public Set<Libro> getLibros() {
		return libros;
	}

	public void setLibros(Set<Libro> libros) {
		this.libros = libros;
	}
}
