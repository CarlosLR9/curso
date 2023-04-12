package com.example.application.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(FilmResource.class)
class FilmResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private FilmService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Value
	static class FilmShortMock implements FilmShort {
		int filmId;
		String title;
	}
	
	@Test
	void testGetAllString() throws Exception {
		List<FilmShort> lista = new ArrayList<>(
		        Arrays.asList(new FilmShortMock(1, "Pinocho"),
		        		new FilmShortMock(2, "Peter Pan"),
		        		new FilmShortMock(3, "Dumbo")));
		when(srv.getByProjection(FilmShort.class)).thenReturn(lista);
		mockMvc.perform(get("/api/pelis/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<FilmShort> lista = new ArrayList<>(
		        Arrays.asList(new FilmShortMock(1, "Pinocho"),
		        		new FilmShortMock(2, "Peter Pan"),
		        		new FilmShortMock(3, "Dumbo")));
		when(srv.getByProjection(PageRequest.of(0, 20), FilmShort.class))
			.thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/pelis/v1").queryParam("page", "0"))
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
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/pelis/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.filmId").value(id))
	        .andExpect(jsonPath("$.title").value(ele.getTitle()))
	        .andDo(print());
	}
	
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/pelis/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}
	
	@Test
	void testGetActores() throws Exception {
		int id = 1;
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		ele.addActor(new Actor(1, "Pepito", "Grillo"));
		ele.addActor(new Actor(2, "Jiminy", "Cricket"));
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/pelis/v1/{id}/actores", id).accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.size()").value(2)
				)
			.andDo(print());
	}
	
	@Test
	void testGetCategorias() throws Exception {
		int id = 1;
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		ele.addCategory(new Category(1, "Animacion"));
		ele.addCategory(new Category(2, "Drama"));
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/pelis/v1/{id}/categorias", id).accept(MediaType.APPLICATION_JSON))
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
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/pelis/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(FilmEditDTO.from(ele)))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/pelis/v1/1"))
	        .andDo(print())
	        ;
	}

	@Test
	void testUpdate() throws Exception {
		int id = 1;
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		when(srv.add(ele)).thenReturn(ele);
		ele.setTitle("Pinocchio");
		when(srv.modify(ele)).thenReturn(ele);
		mockMvc.perform(put("/api/pelis/v1//{id}", id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(FilmEditDTO.from(ele)))
			)
			.andExpect(status().isNoContent())
	        .andDo(print())
	        ;
	}
	
	@Test
	void testUpdate404() throws Exception {
		int id = 1;
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		when(srv.add(ele)).thenReturn(ele);
		ele.setTitle("Pinocchio");
		when(srv.modify(ele)).thenReturn(null);
		mockMvc.perform(get("/api/pelis/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		int id = 1;
		var ele = new Film(id, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(delete("/api/pelis/v1//{id}", id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(srv.getOne(1)))
			)
			.andExpect(status().isNoContent())
	        .andDo(print())
	        ;
	}
	
}