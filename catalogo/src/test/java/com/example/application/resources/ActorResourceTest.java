package com.example.application.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.ActorEditDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Value
	static class ActorShortMock implements ActorShort {
		int actorId;
		String nombre;
	}
	
	@Test
	void testGetAllString() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Carmelo Coton"),
		        		new ActorShortMock(3, "Capitan Tan")));
		when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
		mockMvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Carmelo Coton"),
		        		new ActorShortMock(3, "Capitan Tan")));

		when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class))
			.thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3)
				);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
	        .andExpect(jsonPath("$.apellidos").value(ele.getLastName()))
	        .andDo(print());
	}
	
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}
	
	@Test
	void testGetPelis() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		ele.addFilm(new Film(1, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES));
		ele.addFilm(new Film(2, "Dumbo", "Un elefante con orejas grandes", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES));
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/actores/v1/{id}/pelis", id).accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.size()").value(2)
				)
			.andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/actores/v1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorEditDTO.from(ele)))
				)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/actores/v1/1"))
	        .andDo(print())
	        ;
	}

	@Test
	void testUpdate() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.modify(ele)).thenReturn(ele);
		mockMvc.perform(put("/api/actores/v1//{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorEditDTO.from(ele)))
				)
			.andExpect(status().isNoContent())
	        .andDo(print())
	        ;
	}
	
	@Test
	void testUpdate400() throws Exception {
		int id = 1;
		var ele = new Actor(2, "Pepito", "Grillo");
		when(srv.add(ele)).thenReturn(ele);
		ele.setFirstName("Jiminy");
		when(srv.modify(ele)).thenReturn(ele);
		mockMvc.perform(put("/api/actores/v1//{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorEditDTO.from(ele)))
				)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.title").value("Bad Request"))
	        .andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.add(ele)).thenReturn(ele);
		doNothing().when(srv).deleteById(id);
		mockMvc.perform(delete("/api/actores/v1//{id}", id)
			.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNoContent())
	        .andDo(print())
	        ;
	}
	
}

