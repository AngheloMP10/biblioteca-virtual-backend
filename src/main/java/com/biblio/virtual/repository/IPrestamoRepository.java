package com.biblio.virtual.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.model.enums.EstadoPrestamo;

public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findByUsuarioUsername(String username);

	// Préstamos activos del usuario
	long countByUsuarioUsernameAndEstadoNotIn(
			String username,
			List<EstadoPrestamo> estados);

}
