package com.biblio.virtual.mapper;

import com.biblio.virtual.dto.PrestamoDTO;
import com.biblio.virtual.model.Prestamo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class, LibroMapper.class })
public interface PrestamoMapper {

	PrestamoDTO toDto(Prestamo prestamo);

	List<PrestamoDTO> toDtoList(List<Prestamo> prestamos);
}
