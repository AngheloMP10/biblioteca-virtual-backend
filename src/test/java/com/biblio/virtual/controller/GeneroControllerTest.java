package com.biblio.virtual.controller;

import com.biblio.virtual.dto.GeneroDTO;
import com.biblio.virtual.mapper.GeneroMapper;
import com.biblio.virtual.model.Genero;
import com.biblio.virtual.service.IGeneroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(GeneroController.class)
class GeneroControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IGeneroService generoService;

	@MockBean
	private GeneroMapper generoMapper;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void deberiaListarGeneros() throws Exception {
		Genero g1 = new Genero();
		g1.setId(1L);
		g1.setNombre("Drama");

		Genero g2 = new Genero();
		g2.setId(2L);
		g2.setNombre("Comedia");

		List<Genero> generos = Arrays.asList(g1, g2);

		GeneroDTO dto1 = new GeneroDTO();
		dto1.setId(1L);
		dto1.setNombre("Drama");

		GeneroDTO dto2 = new GeneroDTO();
		dto2.setId(2L);
		dto2.setNombre("Comedia");

		List<GeneroDTO> dtos = Arrays.asList(dto1, dto2);

		when(generoService.findAll()).thenReturn(generos);
		when(generoMapper.toDtoList(generos)).thenReturn(dtos);

		mockMvc.perform(get("/generos"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nombre").value("Drama"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].nombre").value("Comedia"));
	}

	@Test
	@WithMockUser(authorities = "USER")
	void deberiaPermitirListarGenerosConRolUser() throws Exception {
		when(generoService.findAll()).thenReturn(List.of());
		when(generoMapper.toDtoList(List.of())).thenReturn(List.of());

		mockMvc.perform(get("/generos"))
				.andExpect(status().isOk());

		verify(generoService, times(1)).findAll();
	}

	@Test
	void deberiaBuscarGeneroPorId() throws Exception {
		Genero genero = new Genero();
		genero.setId(1L);
		genero.setNombre("Terror");

		GeneroDTO dto = new GeneroDTO();
		dto.setId(1L);
		dto.setNombre("Terror");

		when(generoService.findById(1L)).thenReturn(genero);
		when(generoMapper.toDto(genero)).thenReturn(dto);

		mockMvc.perform(get("/generos/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("Terror"));
	}

	@Test
	void deberiaRetornar404CuandoGeneroNoExisteAlBuscarPorId() throws Exception {
		when(generoService.findById(999L)).thenReturn(null);

		mockMvc.perform(get("/generos/999"))
				.andExpect(status().isNotFound());

		verify(generoService, times(1)).findById(999L);
		verify(generoMapper, never()).toDto(any());
	}

	@Test
	void deberiaCrearGenero() throws Exception {
		GeneroDTO requestDto = new GeneroDTO();
		requestDto.setNombre("Aventura");

		Genero entity = new Genero();
		entity.setNombre("Aventura");

		Genero savedEntity = new Genero();
		savedEntity.setId(1L);
		savedEntity.setNombre("Aventura");

		GeneroDTO responseDto = new GeneroDTO();
		responseDto.setId(1L);
		responseDto.setNombre("Aventura");

		when(generoMapper.toEntity(any(GeneroDTO.class))).thenReturn(entity);
		when(generoService.save(entity)).thenReturn(savedEntity);
		when(generoMapper.toDto(savedEntity)).thenReturn(responseDto);

		mockMvc.perform(post("/generos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("Aventura"));

		verify(generoService, times(1)).save(entity);
	}

	@Test
	@WithMockUser(authorities = "USER")
	void deberiaDenegarCrearGeneroConRolUser() throws Exception {
		GeneroDTO dto = new GeneroDTO();
		dto.setNombre("Drama");

		mockMvc.perform(post("/generos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isForbidden());

		verify(generoService, never()).save(any());
	}

	@Test
	void deberiaActualizarGenero() throws Exception {
		Genero existente = new Genero();
		existente.setId(1L);
		existente.setNombre("Sci-Fi");

		GeneroDTO requestDto = new GeneroDTO();
		requestDto.setNombre("Ciencia Ficción");

		Genero actualizado = new Genero();
		actualizado.setId(1L);
		actualizado.setNombre("Ciencia Ficción");

		GeneroDTO responseDto = new GeneroDTO();
		responseDto.setId(1L);
		responseDto.setNombre("Ciencia Ficción");

		when(generoService.findById(1L)).thenReturn(existente);
		when(generoService.save(any(Genero.class))).thenReturn(actualizado);
		when(generoMapper.toDto(actualizado)).thenReturn(responseDto);

		mockMvc.perform(put("/generos/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nombre").value("Ciencia Ficción"));

		verify(generoService, times(1)).save(any(Genero.class));
	}

	@Test
	void deberiaRetornar404CuandoGeneroNoExisteAlActualizar() throws Exception {
		GeneroDTO requestDto = new GeneroDTO();
		requestDto.setNombre("Nuevo Nombre");

		when(generoService.findById(999L)).thenReturn(null);

		mockMvc.perform(put("/generos/999")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
				.andExpect(status().isNotFound());

		verify(generoService, times(1)).findById(999L);
		verify(generoService, never()).save(any());
		verify(generoMapper, never()).toDto(any());
	}

	@Test
	void deberiaEliminarGenero() throws Exception {
		Genero existente = new Genero();
		existente.setId(1L);
		existente.setNombre("Romance");

		when(generoService.findById(1L)).thenReturn(existente);

		mockMvc.perform(delete("/generos/1"))
				.andExpect(status().isNoContent());

		verify(generoService, times(1)).delete(1L);
	}

	@Test
	void deberiaRetornar404CuandoGeneroNoExisteAlEliminar() throws Exception {
		when(generoService.findById(999L)).thenReturn(null);

		mockMvc.perform(delete("/generos/999"))
				.andExpect(status().isNotFound());

		verify(generoService, times(1)).findById(999L);
		verify(generoService, never()).delete(anyLong());
	}

	@Test
	void deberiaDenegarAccesoSinAutenticacion() throws Exception {
		mockMvc.perform(get("/generos"))
				.andExpect(status().isForbidden());
	}
}