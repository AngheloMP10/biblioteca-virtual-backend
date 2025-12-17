package com.biblio.virtual.mapper;

import com.biblio.virtual.dto.UsuarioDTO;
import com.biblio.virtual.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	UsuarioDTO toDto(Usuario usuario);
}
