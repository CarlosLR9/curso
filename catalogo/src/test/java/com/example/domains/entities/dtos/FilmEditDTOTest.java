package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;

class FilmEditDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFromFilm() {
		var film = new Film(0, "Pinocho",
				"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", (short) 1940,
				new Language(1), new Language(2), (byte) 2, new BigDecimal(1), 80, new BigDecimal(20),
				Rating.GENERAL_AUDIENCES);
		film.addActor(1);
		film.addCategory(1);
		var filmDTO = FilmEditDTO.from(film);
		
		assertAll("Film",
				() -> assertEquals(FilmEditDTO.class, filmDTO.getClass()),
				() -> assertEquals(0, filmDTO.getFilmId()),
				() -> assertEquals("Pinocho", filmDTO.getTitle()),
				() -> assertEquals("Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", filmDTO.getDescription()),
				() -> assertEquals((short) 1940, filmDTO.getReleaseYear()),
				() -> assertEquals(1, filmDTO.getLanguageId()),
				() -> assertEquals(2, filmDTO.getLanguageVOId()),
				() -> assertEquals((byte) 2, filmDTO.getRentalDuration()),
				() -> assertEquals(new BigDecimal(1), filmDTO.getRentalRate()),
				() -> assertEquals(80, filmDTO.getLength()),
				() -> assertEquals(new BigDecimal(20), filmDTO.getReplacementCost()),
				() -> assertEquals("G", filmDTO.getRating()),
				() -> assertEquals(1, filmDTO.getActors().get(0)),
				() -> assertEquals(1, filmDTO.getCategories().get(0))
				);
	}
	
	@Test
	void testFromFilmEditDTO() {
		var listA =  new ArrayList<Integer>();
		listA.add(1);
		var listC =  new ArrayList<Integer>();
		listC.add(1);
		var languageDTO = new FilmEditDTO(0, "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho",
				80, "G", (short) 1940, (byte) 2, new BigDecimal(1), new BigDecimal(20), "Pinocho", 1, 2, listA, listC);
		var film = FilmEditDTO.from(languageDTO);
		
		assertAll("ActorEditDTO",
				() -> assertEquals(Film.class, film.getClass()),
				() -> assertEquals(0, film.getFilmId()),
				() -> assertEquals("Pinocho", film.getTitle()),
				() -> assertEquals("Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", film.getDescription()),
				() -> assertEquals((short) 1940, film.getReleaseYear()),
				() -> assertEquals(1, film.getLanguage().getLanguageId()),
				() -> assertEquals(2, film.getLanguageVO().getLanguageId()),
				() -> assertEquals((byte) 2, film.getRentalDuration()),
				() -> assertEquals(new BigDecimal(1), film.getRentalRate()),
				() -> assertEquals(80, film.getLength()),
				() -> assertEquals(new BigDecimal(20), film.getReplacementCost()),
				() -> assertEquals(Rating.GENERAL_AUDIENCES, film.getRating()),
				() -> assertEquals(1, film.getActors().get(0).getActorId()),
				() -> assertEquals(1, film.getCategories().get(0).getCategoryId())
				);
	}

}
