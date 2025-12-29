package com.biblio.virtual.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.biblio.virtual.dto.PrestamoDTO;
import com.biblio.virtual.mapper.PrestamoMapper;
import com.biblio.virtual.service.IPrestamoService;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

	private final IPrestamoService prestamoService;
	private final PrestamoMapper prestamoMapper;

	public PrestamoController(IPrestamoService prestamoService, PrestamoMapper prestamoMapper) {
		this.prestamoService = prestamoService;
		this.prestamoMapper = prestamoMapper;
	}

	// SOLICITAR PRÉSTAMO: USER o ADMIN
	@PreAuthorize("hasAnyAuthority(@roles.USER(), @roles.ADMIN())")
	@PostMapping("/solicitar/{libroId}")
	public ResponseEntity<?> solicitarPrestamo(@PathVariable Long libroId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		prestamoService.solicitarPrestamo(libroId, username);
		return ResponseEntity.ok("Solicitud de préstamo enviada con éxito");
	}

	// LISTAR TODOS
	@PreAuthorize("hasAnyAuthority(@roles.BIBLIOTECARIO(), @roles.ADMIN())")
	@GetMapping("/todos")
	public ResponseEntity<List<PrestamoDTO>> listarTodos() {
		return ResponseEntity.ok(prestamoMapper.toDtoList(prestamoService.findAll()));
	}

	// MIS PRÉSTAMOS
	@PreAuthorize("hasAnyAuthority(@roles.USER(), @roles.BIBLIOTECARIO(), @roles.ADMIN())")
	@GetMapping("/mios")
	public ResponseEntity<List<PrestamoDTO>> misPrestamos() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		return ResponseEntity.ok(prestamoMapper.toDtoList(prestamoService.findByUsername(username)));
	}

	// APROBAR: ADMIN o BIBLIOTECARIO
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO())")
	@PostMapping("/aprobar/{id}")
	public ResponseEntity<?> aprobarPrestamo(@PathVariable Long id) {

		prestamoService.aprobarPrestamo(id);
		return ResponseEntity.ok("Préstamo aprobado.");
	}

	// ENTREGAR / RETIRO PRESENCIAL (QR)
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO())")
	@PostMapping("/entregar/{id}")
	public ResponseEntity<?> entregarPrestamo(@PathVariable Long id) {

		prestamoService.entregarPrestamo(id);
		return ResponseEntity.ok("Libro entregado al usuario. Préstamo activo.");
	}

	// RECHAZAR: ADMIN o BIBLIOTECARIO
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO())")
	@PostMapping("/rechazar/{id}")
	public ResponseEntity<?> rechazarPrestamo(@PathVariable Long id) {

		prestamoService.rechazarPrestamo(id);
		return ResponseEntity.ok("Solicitud rechazada.");
	}

	// FINALIZAR / DEVOLVER: ADMIN o BIBLIOTECARIO
	@PreAuthorize("hasAnyAuthority(@roles.ADMIN(), @roles.BIBLIOTECARIO())")
	@PostMapping("/finalizar/{id}")
	public ResponseEntity<?> finalizarPrestamo(@PathVariable Long id) {

		prestamoService.finalizarPrestamo(id);
		return ResponseEntity.ok("Libro devuelto correctamente.");
	}
}
