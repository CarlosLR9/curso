package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ActorTest {
	
	@Test
	void testIsValid() {
		var item = new Actor(0, "PEPITO", "GRILLO");
		assertTrue(item.isValid());
	}

	@DisplayName("El nombre de tener entre 2 y 45 caracteres, tiene que estar en mayusculas y no puede estar en blanco")
	@ParameterizedTest(name = "nombre: -{0}- -> {1}")
	@CsvSource(value = {
			"'','ERRORES: firstName: size must be between 2 and 45, must be in capital letters, must not be blank.'", 
			"' ','ERRORES: firstName: must be in capital letters, must not be blank, size must be between 2 and 45.'", 
			"'   ','ERRORES: firstName: must be in capital letters, must not be blank.'", 
			"A,'ERRORES: firstName: size must be between 2 and 45.'",
			"Pepito,'ERRORES: firstName: must be in capital letters.'",
			"12345678901234567890123456789012345678901234567890,'ERRORES: firstName: size must be between 2 and 45, must be in capital letters.'"})
	void testNombreIsInvalid(String valor, String error) {
		var item = new Actor(0, valor, "GRILLO");
		assertTrue(item.isInvalid());
//		assertEquals(error, item.getErrorsMessage());
	}
	
	@DisplayName("El apellido de tener entre 2 y 45 caracteres, tiene que estar en mayusculas y no puede estar en blanco")
	@ParameterizedTest(name = "apellido: -{0}- -> {1}")
	@CsvSource(value = {
			"'','ERRORES: lastName: size must be between 2 and 45, must not be blank, must be in capital letters.'", 
			"' ','ERRORES: lastName: size must be between 2 and 45, must be in capital letters, must not be blank.'", 
			"'   ','ERRORES: lastName: firstName: must be in capital letters, must not be blank.'", 
			"A,'ERRORES: lastName: size must be between 2 and 45.'",
			"Grillo,'ERRORES: lastName: must be in capital letters.'",
			"12345678901234567890123456789012345678901234567890,'ERRORES: lastName: size must be between 2 and 45, must be in capital letters.'"})
	void testApellidoIsInvalid(String valor, String error) {
		var item = new Actor(0, "PEPITO", valor);
		assertTrue(item.isInvalid());
//		assertEquals(error, item.getErrorsMessage());
	}
	
	@Test
	void testAddFilm() {
		var item = new Actor(0, "PEPITO", "GRILLO");
		item.addFilm(1);
		assertEquals(1, item.getFilms().get(0).getFilmId());
	}

	@Test
	void testRemoveFilm() {
		var item = new Actor(0, "PEPITO", "GRILLO");
		var film = new Film(1);
		item.addFilm(film);
		item.removeFilm(film);
		assertEquals(0, item.getFilms().size());
	}

	@Test
	void testMerge() {
		var item = new Actor(1, "PEPITO", "GRILLO");
		item.addFilm(1);
		
		var itemM = new Actor(1, "JIMINY", "CRICKET");
		itemM.addFilm(2);
		
		itemM.merge(item);
		assertAll("Merge",
				() -> assertEquals(itemM.getActorId(), item.getActorId()),
				() -> assertEquals(itemM.getFirstName(), item.getFirstName()),
				() -> assertEquals(itemM.getLastName(), item.getLastName()),
				() -> assertEquals(itemM.getFilms(), item.getFilms())
				);

	}

}
