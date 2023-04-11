package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LanguageTest {

	@Test
	void testIsValid() {
		var item = new Language(0, "Español");
		assertTrue(item.isValid());
	}

	@Test
	void testNombreIsInvalid() {
		var item = new Category(0, " ");
		assertTrue(item.isInvalid());
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
	void testMerge() {
		var item = new Language(0, "Español");
		item.addFilm(1);
		
		var itemM = new Language(0, "Spain");
		itemM.addFilm(2);
		
		itemM.merge(item);
		assertAll("Merge",
				() -> assertEquals(itemM.getLanguageId(), item.getLanguageId()),
				() -> assertEquals(itemM.getName(), item.getName()),
				() -> assertEquals(itemM.getFilms(), item.getFilms()),
				() -> assertEquals(itemM.getFilmsVO(), item.getFilmsVO())
				);
	}
}
