package com.biblio.virtual.controller;

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

	public AutorController(IAutorService service) {
		this.service = service;
	}

	// Creación restringida a ADMIN para control del catálogo
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Autor> guardar(@RequestBody Autor autor) {

		// Se asegura inicialización para evitar errores de persistencia
		if (autor.getLibros() == null) {
			autor.setLibros(new java.util.ArrayList<>());
		}

		Autor nuevoAutor = service.save(autor);
		return ResponseEntity.ok(nuevoAutor);
	}

	// Acceso de lectura permitido a ADMIN y USER
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
		Autor autor = service.findById(id);
		return (autor != null)
				? ResponseEntity.ok(autor)
				: ResponseEntity.notFound().build();
	}

	// Listado completo accesible para usuarios autenticados
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<Autor>> listar() {
		return ResponseEntity.ok(service.findAll());
	}

	// Actualización limitada a ADMIN para mantener integridad
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Autor> actualizar(
			@PathVariable Long id,
			@RequestBody Autor autorActualizado) {

		Autor autor = service.findById(id);
		if (autor == null) {
			return ResponseEntity.notFound().build();
		}

		// Solo se sobrescriben campos editables
		autor.setNombre(autorActualizado.getNombre());
		autor.setUrlFoto(autorActualizado.getUrlFoto());

		if (autorActualizado.getLibros() != null) {
			autor.setLibros(autorActualizado.getLibros());
		}

		Autor actualizado = service.save(autor);
		return ResponseEntity.ok(actualizado);
	}

	// Eliminación exclusiva de ADMIN por impacto en el dominio
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