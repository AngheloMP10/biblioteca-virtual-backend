package com.biblio.virtual.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.model.enums.EstadoPrestamo;

import com.biblio.virtual.dto.DashboardLibroDTO;
import com.biblio.virtual.dto.DashboardGeneroDTO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findByUsuarioUsername(String username);

	// Préstamos activos del usuario
	long countByUsuarioUsernameAndEstadoNotIn(
			String username,
			List<EstadoPrestamo> estados);

	@Query("""
			SELECT new com.biblio.virtual.dto.DashboardLibroDTO(
			    p.libro.titulo,
			    COUNT(p)
			)
			FROM Prestamo p
			GROUP BY p.libro.titulo
			ORDER BY COUNT(p) DESC
			""")
	List<DashboardLibroDTO> obtenerLibrosMasPrestados();

	@Query("""
			SELECT new com.biblio.virtual.dto.DashboardGeneroDTO(
			    p.libro.genero.nombre,
			    COUNT(p)
			)
			FROM Prestamo p
			GROUP BY p.libro.genero.nombre
			ORDER BY COUNT(p) DESC
			""")
	List<DashboardGeneroDTO> obtenerGenerosMasPopulares();

	@Query("""
			    SELECT new com.biblio.virtual.dto.DashboardLibroDTO(
			        p.libro.titulo,
			        COUNT(p)
			    )
			    FROM Prestamo p
			    WHERE p.fechaSolicitud BETWEEN :fechaInicio AND :fechaFin
			    GROUP BY p.libro.titulo
			    ORDER BY COUNT(p) DESC
			""")
	List<DashboardLibroDTO> obtenerLibrosMasPrestadosPorFecha(
			@Param("fechaInicio") LocalDate fechaInicio,
			@Param("fechaFin") LocalDate fechaFin);

	@Query("""
			    SELECT new com.biblio.virtual.dto.DashboardGeneroDTO(
			        p.libro.genero.nombre,
			        COUNT(p)
			    )
			    FROM Prestamo p
			    WHERE p.fechaSolicitud BETWEEN :fechaInicio AND :fechaFin
			    GROUP BY p.libro.genero.nombre
			    ORDER BY COUNT(p) DESC
			""")
	List<DashboardGeneroDTO> obtenerGenerosMasPopularesPorFecha(
			@Param("fechaInicio") LocalDate fechaInicio,
			@Param("fechaFin") LocalDate fechaFin);

	long countByEstadoIn(List<EstadoPrestamo> estados);

	long countByEstado(EstadoPrestamo estado);
}
