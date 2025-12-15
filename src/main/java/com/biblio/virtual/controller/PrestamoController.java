package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.service.IPrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

	private final IPrestamoService prestamoService;

	public PrestamoController(IPrestamoService prestamoService) {
		this.prestamoService = prestamoService;
	}

	// Solicitud de préstamo accesible a USER y ADMIN
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
	@PostMapping("/solicitar/{libroId}")
	public ResponseEntity<?> solicitarPrestamo(@PathVariable Long libroId) {

		// Obtenemos usuario autenticado desde el contexto de seguridad
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		prestamoService.solicitarPrestamo(libroId, username);
		return ResponseEntity.ok("Solicitud de préstamo enviada con éxito");
	}

	// Listado completo de préstamos, solo para ADMIN
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/todos")
	public ResponseEntity<List<Prestamo>> listarTodos() {
		return ResponseEntity.ok(prestamoService.findAll());
	}

	// Listado de préstamos del usuario actual
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/mios")
	public ResponseEntity<List<Prestamo>> misPrestamos() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		return ResponseEntity.ok(prestamoService.findByUsername(username));
	}

	// Aprobación de préstamo, operación crítica restringida a ADMIN
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/aprobar/{id}")
	public ResponseEntity<?> aprobarPrestamo(@PathVariable Long id) {
		prestamoService.aprobarPrestamo(id);
		return ResponseEntity.ok("Préstamo aprobado y libro entregado.");
	}

	// Rechazo de préstamo, solo ADMIN puede realizar
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/rechazar/{id}")
	public ResponseEntity<?> rechazarPrestamo(@PathVariable Long id) {
		prestamoService.rechazarPrestamo(id);
		return ResponseEntity.ok("Solicitud rechazada.");
	}
}