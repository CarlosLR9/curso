package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LanguageTest {

	@Test
	void testIsValid() {
		var item = new Language(0, "Español");
		assertTrue(item.isValid());
	}

	@ParameterizedTest(name = "nombre: -{0}- => {1}")
	@CsvSource(value = { 
			"' ','ERRORES: name: must not be blank.'",
			"123456789012345678901,'ERRORES: name: size must be between 0 and 20.'" })
	void testNombreIsInvalid(String valor, String error) {
		var item = new Language(0, valor);
		assertTrue(item.isInvalid());
		assertEquals(error, item.getErrorsMessage());
	}

	@Test
	void testAddFilm() {
		var item = new Language(0, "Español");
		item.addFilm(1);
		assertEquals(1, item.getFilms().get(0).getFilmId());
	}

	@Test
	void testRemoveFilm() {
		var item = new Language(0, "Español");
		var film = new Film(1);
		item.addFilm(film);
		item.removeFilm(film);
		assertEquals(0, item.getFilms().size());
	}

	@Test
	void testAddFilmVO() {
		var item = new Language(0, "Español");
		item.addFilmVO(1);
		assertEquals(1, item.getFilmsVO().get(0).getFilmId());
	}

	@Test
	void testRemoveFilmVO() {
		var item = new Language(0, "Español");
		var film = new Film(1);
		item.addFilmVO(film);
		item.removeFilmVO(film);
		assertEquals(0, item.getFilmsVO().size());
	}

	@Test
	void testMerge() {
		var item = new Language(0, "Español");
		item.addFilm(1);
		item.addFilmVO(3);

		var itemM = new Language(0, "Spain");
		itemM.addFilm(2);
		itemM.addFilmVO(4);

		itemM.merge(item);
		assertAll("Merge", 
				() -> assertEquals(itemM.getLanguageId(), item.getLanguageId()),
				() -> assertEquals(itemM.getName(), item.getName()),
				() -> assertEquals(itemM.getFilms(), item.getFilms()),
				() -> assertEquals(itemM.getFilmsVO(), item.getFilmsVO()));
	}
}
