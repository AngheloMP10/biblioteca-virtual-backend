package com.biblio.virtual.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.dto.LibroDTO;
import com.biblio.virtual.mapper.LibroMapper;
import com.biblio.virtual.model.Libro;
import com.biblio.virtual.service.ILibroService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/libros")
public class LibrosController {

	private final ILibroService libroService;
	private final LibroMapper libroMapper;

	public LibrosController(ILibroService libroService, LibroMapper libroMapper) {
		this.libroService = libroService;
		this.libroMapper = libroMapper;
	}

	// CREATE: ADMIN o BIBLIOTECARIO
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO())")
	@PostMapping
	public ResponseEntity<LibroDTO> guardar(@RequestBody LibroDTO libroDto) {

		Libro libro = libroMapper.toEntity(libroDto);

		// Portada por defecto
		if (libro.getPortada() == null || libro.getPortada().isEmpty()) {
			libro.setPortada("_default.jpg");
		}

		Libro guardado = libroService.save(libro);
		return ResponseEntity.ok(libroMapper.toDto(guardado));
	}

	// READ - Listar
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO(), @roles.USER())")
	@GetMapping
	public ResponseEntity<Page<LibroDTO>> listar(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, @RequestParam(defaultValue = "id") String order,
			@RequestParam(defaultValue = "true") boolean asc, @RequestParam(required = false) String keyword) {

		Sort sort = asc ? Sort.by(order).ascending() : Sort.by(order).descending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Libro> librosPage = (keyword != null && !keyword.trim().isEmpty())
				? libroService.buscarPorTitulo(keyword, pageable)
				: libroService.findAll(pageable);

		return ResponseEntity.ok(librosPage.map(libroMapper::toDto));
	}

	// READ - por ID
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO(), @roles.USER())")
	@GetMapping("/{id}")
	public ResponseEntity<LibroDTO> buscarPorId(@PathVariable Long id) {
		Libro libro = libroService.findById(id);
		return (libro != null) ? ResponseEntity.ok(libroMapper.toDto(libro)) : ResponseEntity.notFound().build();
	}

	// UPDATE: ADMIN o BIBLIOTECARIO
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO())")
	@PutMapping("/{id}")
	public ResponseEntity<LibroDTO> actualizar(@PathVariable Long id, @RequestBody LibroDTO libroDto) {

		Libro existente = libroService.findById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		existente.setTitulo(libroDto.getTitulo());
		existente.setPortada(libroDto.getPortada());
		existente.setAnioPublicacion(libroDto.getAnioPublicacion());
		existente.setStock(libroDto.getStock());

		if (libroDto.getAutores() != null) {
			existente.setAutores(libroMapper.toEntity(libroDto).getAutores());
		}

		if (libroDto.getGenero() != null) {
			existente.setGenero(libroMapper.toEntity(libroDto).getGenero());
		}

		Libro actualizado = libroService.save(existente);
		return ResponseEntity.ok(libroMapper.toDto(actualizado));
	}

	// DELETE: SOLO ADMIN
	@PreAuthorize("hasAuthority(@roles.ADMIN())")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {

		Libro existente = libroService.findById(id);
		if (existente == null) {
			return ResponseEntity.notFound().build();
		}

		libroService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
