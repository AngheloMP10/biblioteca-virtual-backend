package com.biblio.virtual.mapper;

import com.biblio.virtual.dto.AutorDTO;
import com.biblio.virtual.model.Autor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AutorMapper {

	AutorDTO toDto(Autor autor);

	Autor toEntity(AutorDTO dto);

	List<AutorDTO> toDtoList(List<Autor> autores);
}
