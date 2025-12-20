package com.biblio.virtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biblio.virtual.model.Libro;
import com.biblio.virtual.repository.ILibroRepository;

@Service
public class LibroService implements ILibroService {

	@Autowired
	private ILibroRepository libroRepository;

	@Override
	public Libro save(Libro libro) {
		return libroRepository.save(libro);
	}

	@Override
	public Libro findById(Long id) {
		return libroRepository.findById(id).orElse(null);
	}

	@Override
	public List<Libro> findAll() {
		return libroRepository.findAll();
	}

	@Override
	public Page<Libro> findAll(Pageable pageable) {
		return libroRepository.findAll(pageable);
	}

	@Override
	public Page<Libro> buscarPorTitulo(String termino, Pageable pageable) {
		return libroRepository.findByTituloContainingIgnoreCase(termino, pageable);
	}

	@Override
	public void delete(Long id) {
		libroRepository.deleteById(id);
	}
}
