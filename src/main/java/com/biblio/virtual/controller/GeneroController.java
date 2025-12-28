package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.dto.GeneroDTO;
import com.biblio.virtual.mapper.GeneroMapper;
import com.biblio.virtual.model.Genero;
import com.biblio.virtual.service.IGeneroService;

@RestController
@RequestMapping("/generos")
public class GeneroController {

	private final IGeneroService service;
	private final GeneroMapper generoMapper;

	public GeneroController(IGeneroService service, GeneroMapper generoMapper) {
		this.service = service;
		this.generoMapper = generoMapper;
	}

	// CREATE - Solo ADMIN
	@PreAuthorize("hasAuthority(@roles.ADMIN())")
	@PostMapping
	public ResponseEntity<GeneroDTO> guardar(@RequestBody GeneroDTO generoDto) {

		Genero genero = generoMapper.toEntity(generoDto);
		Genero guardado = service.save(genero);

		return ResponseEntity.ok(generoMapper.toDto(guardado));
	}

	// READ - Listar
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.USER())")
	@GetMapping
	public ResponseEntity<List<GeneroDTO>> listar() {
		return ResponseEntity.ok(generoMapper.toDtoList(service.findAll()));
	}

	// READ - Buscar por ID
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.USER())")
	@GetMapping("/{id}")
	public ResponseEntity<GeneroDTO> buscarPorId(@PathVariable Long id) {

		Genero genero = service.findById(id);
		return (genero != null)
				? ResponseEntity.ok(generoMapper.toDto(genero))
				: ResponseEntity.notFound().build();
	}

	// UPDATE - Solo ADMIN
	@PreAuthorize("hasAuthority(@roles.ADMIN())")
	@PutMapping("/{id}")
	public ResponseEntity<GeneroDTO> actualizar(@PathVariable Long id,
			@RequestBody GeneroDTO generoDto) {

		Genero existente = service.findById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		existente.setNombre(generoDto.getNombre());
		Genero actualizado = service.save(existente);

		return ResponseEntity.ok(generoMapper.toDto(actualizado));
	}

	// DELETE - Solo ADMIN
	@PreAuthorize("hasAuthority(@roles.ADMIN())")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {

		Genero existente = service.findById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
