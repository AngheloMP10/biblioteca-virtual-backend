package com.biblio.virtual.service;

import java.util.List;
import com.biblio.virtual.model.Prestamo;

public interface IPrestamoService {

	// Solicitar préstamo
	Prestamo solicitarPrestamo(Long libroId, String username);

	// Listar todos (ADMIN)
	List<Prestamo> findAll();

	// Listar por usuario (USER)
	List<Prestamo> findByUsername(String username);

	void aprobarPrestamo(Long id);

	void rechazarPrestamo(Long id);

	void finalizarPrestamo(Long id);
}
