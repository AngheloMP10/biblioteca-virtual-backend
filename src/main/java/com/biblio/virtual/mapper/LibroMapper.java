package com.biblio.virtual.mapper;

import com.biblio.virtual.dto.LibroDTO;
import com.biblio.virtual.model.Libro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AutorMapper.class, GeneroMapper.class })
public interface LibroMapper {

	// Entidad a DTO
	LibroDTO toDto(Libro libro);

	// DTO a Entidad
	@Mapping(target = "prestamos", ignore = true)
	Libro toEntity(LibroDTO dto);

	// Listas
	List<LibroDTO> toDtoList(List<Libro> libros);
}
