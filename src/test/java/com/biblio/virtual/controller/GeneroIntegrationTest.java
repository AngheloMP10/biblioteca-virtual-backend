package com.biblio.virtual.controller;

import com.biblio.virtual.dto.GeneroDTO;
import com.biblio.virtual.model.Genero;
import com.biblio.virtual.repository.IGeneroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GeneroIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private IGeneroRepository generoRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		// Limpiamos la base de datos en memoria antes de cada test
		generoRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ROLE_ADMIN", "ADMIN" })
	void deberiaCrearGeneroYGuardarloEnBaseDeDatos() throws Exception {
		// 1. Preparamos el DTO de entrada
		GeneroDTO requestDto = new GeneroDTO();
		requestDto.setNombre("Ciberpunk");

		// 2. Ejecutamos la petición HTTP REAL contra el contexto de Spring
		mockMvc.perform(post("/generos").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Ciberpunk")).andExpect(jsonPath("$.id").exists());

		// 3. LA PRUEBA DE FUEGO (Integración real):
		// Vamos a la base de datos a ver si de verdad se guardó
		assertEquals(1, generoRepository.count(), "Debe haber exactamente 1 género en la BD");

		Genero guardado = generoRepository.findAll().get(0);
		assertEquals("Ciberpunk", guardado.getNombre(), "El nombre en la BD debe coincidir");
	}
}