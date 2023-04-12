package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

	@Test
	void testIsValid() {
		var item = new Category(0, "Animacion");
		assertTrue(item.isValid());
	}

	@ParameterizedTest(name = "nombre: -{0}- => {1}")
	@CsvSource(value = { 
			"' ','ERRORES: name: must not be blank.'",
			"12345678901234567890123456,'ERRORES: name: size must be between 0 and 25.'" })
	void testNombreIsInvalid(String valor, String error) {
		var item = new Category(0, valor);
		assertTrue(item.isInvalid());
		assertEquals(error, item.getErrorsMessage());
	}

	@Test
	void testAddFilm() {
		var item = new Category(0, "Animacion");
		item.addFilm(1);
		assertEquals(1, item.getFilms().get(0).getFilmId());
	}

	@Test
	void testRemoveFilm() {
		var item = new Category(0, "Animacion");
		var film = new Film(1);
		item.addFilm(film);
		item.removeFilm(film);
		assertEquals(0, item.getFilms().size());
	}

	@Test
	void testMerge() {
		var item = new Category(0, "Animacion");
		item.addFilm(1);

		var itemM = new Category(0, "Animation");
		itemM.addFilm(2);

		itemM.merge(item);
		assertAll("Merge", 
				() -> assertEquals(itemM.getCategoryId(), item.getCategoryId()),
				() -> assertEquals(itemM.getName(), item.getName()),
				() -> assertEquals(itemM.getFilms(), item.getFilms()));

	}

}
