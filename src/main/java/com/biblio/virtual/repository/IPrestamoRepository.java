package com.biblio.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblio.virtual.model.Prestamo;
import java.util.List;

public interface IPrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findByUsuarioUsername(String username);
}
