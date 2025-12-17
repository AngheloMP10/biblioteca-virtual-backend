package com.biblio.virtual.mapper;

import com.biblio.virtual.dto.GeneroDTO;
import com.biblio.virtual.model.Genero;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeneroMapper {

	GeneroDTO toDto(Genero genero);

	Genero toEntity(GeneroDTO dto);

	List<GeneroDTO> toDtoList(List<Genero> generos);
}
