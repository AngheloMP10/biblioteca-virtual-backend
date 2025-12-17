package com.biblio.virtual.controller;

import com.biblio.virtual.dto.AutorDTO;
import com.biblio.virtual.mapper.AutorMapper;
import com.biblio.virtual.model.Autor;
import com.biblio.virtual.service.IAutorService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

	private final IAutorService service;
	private final AutorMapper autorMapper;

	public AutorController(IAutorService service, AutorMapper autorMapper) {
		this.service = service;
		this.autorMapper = autorMapper;
	}

	// CREATE
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<AutorDTO> guardar(@RequestBody AutorDTO autorDto) {

		Autor autor = autorMapper.toEntity(autorDto);

		// Seguridad defensiva
		if (autor.getLibros() == null) {
			autor.setLibros(new java.util.ArrayList<>());
		}

		Autor guardado = service.save(autor);
		return ResponseEntity.ok(autorMapper.toDto(guardado));
	}

	// READ - por ID
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
		Autor autor = service.findById(id);
		return (autor != null) ? ResponseEntity.ok(autorMapper.toDto(autor)) : ResponseEntity.notFound().build();
	}

	// READ - listar
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<AutorDTO>> listar() {
		return ResponseEntity.ok(autorMapper.toDtoList(service.findAll()));
	}

	// UPDATE
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<AutorDTO> actualizar(@PathVariable Long id, @RequestBody AutorDTO autorDto) {

		Autor existente = service.findById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		existente.setNombre(autorDto.getNombre());
		existente.setUrlFoto(autorDto.getUrlFoto());

		Autor actualizado = service.save(existente);
		return ResponseEntity.ok(autorMapper.toDto(actualizado));
	}

	// DELETE
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {

		Autor autor = service.findById(id);
		if (autor == null) {
			return ResponseEntity.notFound().build();
		}

		service.delete(id);
		return ResponseEntity.ok("Autor eliminado correctamente");
	}
}
