package com.biblio.virtual.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.model.Prestamo;
import com.biblio.virtual.model.Usuario;
import com.biblio.virtual.repository.ILibroRepository;
import com.biblio.virtual.repository.IPrestamoRepository;
import com.biblio.virtual.repository.UsuarioRepository;

@Service
public class PrestamoService implements IPrestamoService {

	private final IPrestamoRepository prestamoRepo;
	private final UsuarioRepository usuarioRepo;
	private final ILibroRepository libroRepo;

	public PrestamoService(IPrestamoRepository prestamoRepo, UsuarioRepository usuarioRepo,
			ILibroRepository libroRepo) {
		this.prestamoRepo = prestamoRepo;
		this.usuarioRepo = usuarioRepo;
		this.libroRepo = libroRepo;
	}

	@Override
	@Transactional
	public Prestamo solicitarPrestamo(Long libroId, String username) {
		// Buscar usuario y libro
		Usuario usuario = usuarioRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
		Libro libro = libroRepo.findById(libroId).orElseThrow(() -> new RuntimeException("Libro no encontrado"));

		// Validar disponibilidad del libro
		if (libro.getStock() <= 0) {
			throw new RuntimeException("El libro no está disponible");
		}

		// Crear solicitud de préstamo pendiente
		Prestamo prestamo = new Prestamo();
		prestamo.setUsuario(usuario);
		prestamo.setLibro(libro);
		prestamo.setFechaSolicitud(LocalDate.now());
		prestamo.setEstado("PENDIENTE");

		return prestamoRepo.save(prestamo);
	}

	@Override
	@Transactional
	public void aprobarPrestamo(Long id) {

		Prestamo prestamo = prestamoRepo.findById(id).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

		Libro libro = prestamo.getLibro();

		if (libro.getStock() <= 0) {
			throw new RuntimeException("No hay stock disponible para este libro");
		}

		prestamo.setEstado("APROBADO");
		prestamo.setFechaDevolucion(LocalDate.now().plusDays(7));

		libro.setStock(libro.getStock() - 1);

		libroRepo.save(libro);
		prestamoRepo.save(prestamo);
	}

	@Override
	@Transactional
	public void rechazarPrestamo(Long id) {
		// Cambiar estado a RECHAZADO
		Prestamo prestamo = prestamoRepo.findById(id).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));
		prestamo.setEstado("RECHAZADO");
		prestamoRepo.save(prestamo);
	}

	@Override
	@Transactional
	public void finalizarPrestamo(Long id) {

		Prestamo prestamo = prestamoRepo.findById(id).orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

		if (!"APROBADO".equals(prestamo.getEstado())) {
			throw new RuntimeException("Solo se pueden finalizar préstamos aprobados");
		}

		// Cambiar estado
		prestamo.setEstado("FINALIZADO");
		prestamo.setFechaDevolucion(LocalDate.now());

		// Recuperar stock
		Libro libro = prestamo.getLibro();
		libro.setStock(libro.getStock() + 1);

		libroRepo.save(libro);
		prestamoRepo.save(prestamo);
	}

	@Override
	public List<Prestamo> findAll() {
		// Listar todos los préstamos
		return prestamoRepo.findAll();
	}

	@Override
	public List<Prestamo> findByUsername(String username) {
		// Listar préstamos de un usuario específico
		return prestamoRepo.findByUsuarioUsername(username);
	}
}