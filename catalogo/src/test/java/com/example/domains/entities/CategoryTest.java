package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CategoryTest {

	@Test
	void testIsValid() {
		var item = new Category(0, "Animacion");
		assertTrue(item.isValid());
	}

	@Test
	void testNombreIsInvalid() {
		var item = new Category(0, " ");
		assertTrue(item.isInvalid());
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
				() -> assertEquals(itemM.getFilms(), item.getFilms())
				);

	}

}
