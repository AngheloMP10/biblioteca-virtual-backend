package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.model.Genero;
import com.biblio.virtual.service.IGeneroService;

@RestController
@RequestMapping("/generos")
public class GeneroController {

	private final IGeneroService service;

	public GeneroController(IGeneroService service) {
		this.service = service;
	}

	// Solo ADMIN puede crear géneros para controlar catálogo
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Genero> guardar(@RequestBody Genero genero) {
		service.save(genero);
		return ResponseEntity.ok(genero);
	}

	// Lectura accesible a ADMIN y USER
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<Genero>> listar() {
		return ResponseEntity.ok(service.findAll());
	}

	// Lectura por ID, restringida a usuarios autenticados
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
		Genero genero = service.findById(id);
		return (genero != null) ? ResponseEntity.ok(genero) : ResponseEntity.notFound().build();
	}

	// Solo ADMIN puede actualizar para mantener integridad del catálogo
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Genero> actualizar(@PathVariable Long id, @RequestBody Genero genero) {
		Genero existente = service.findById(id);
		if (existente != null) {
			existente.setNombre(genero.getNombre()); // Solo actualizamos el nombre
			service.save(existente);
			return ResponseEntity.ok(existente);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Solo ADMIN puede eliminar para evitar inconsistencias
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		Genero existente = service.findById(id);
		if (existente != null) {
			service.delete(id);
			return ResponseEntity.noContent().build(); // Respuesta estándar sin cuerpo
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}