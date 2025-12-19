package com.biblio.virtual.dto;

import java.util.List;

public class LibroDTO {

	private Long id;
	private String titulo;
	private String portada;
	private Integer anioPublicacion;
	private Integer stock;
	private boolean disponible;

	// DTOs anidados
	private GeneroDTO genero;
	private List<AutorDTO> autores;

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public Integer getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public GeneroDTO getGenero() {
		return genero;
	}

	public void setGenero(GeneroDTO genero) {
		this.genero = genero;
	}

	public List<AutorDTO> getAutores() {
		return autores;
	}

	public void setAutores(List<AutorDTO> autores) {
		this.autores = autores;
	}
}