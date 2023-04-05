package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.domains.entities.Film.Rating;

class FilmTest {

	@Nested
	class invalid {
		@ParameterizedTest(name = "title: {0}")
		@CsvSource(value = {
				"''", 
				"'  '", 
				"12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
				})
		void testTitleIsInvalid(String valor) {
			var item = new Film(0, valor, "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
					(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
					80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
			assertTrue(item.isInvalid());
		}
		
		@ParameterizedTest(name = "title: {0}")
		@CsvSource(value = {"0", "-1"})
		void testLengthIsInvalid(int valor) {
			var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
					(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
					valor, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
			assertTrue(item.isInvalid());
		}
		
		@ParameterizedTest(name = "title: {0}")
		@CsvSource(value = {"1000", "1894"})
		void testReleaseYearIsInvalid(Short valor) {
			var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
					valor, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
					80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
			assertTrue(item.isInvalid());
		}
		
		@ParameterizedTest(name = "title: {0}")
		@CsvSource(value = {"0", "-1"})
		void testReleaseYearIsInvalid(Byte valor) {
			var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
					(short) 1940, new Language(1), new Language(2), valor, new BigDecimal(2), 
					80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
			assertTrue(item.isInvalid());
		}
	}
	@Test
	void testIsValid() {
		var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		assertTrue(item.isValid());
	}

	@Test
	void testAddActor() {
		var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		item.addActor(1);
		assertEquals(1, item.getActors().get(0).getActorId());
	}

	@Test
	void testRemoveActor() {
		var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		var actor = new Actor(0, "PEPITO", "GRILLO");
		item.addActor(actor);
		item.removeActor(actor);
		assertEquals(0, item.getActors().size());
	}

	@Test
	void testMerge() {
		var item = new Film(0, "Pinocho", "Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		item.addActor(1);
		
		var itemM = new Film(0, "Pinocchio", "A pupet wants to be human", 
				(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 
				80, new BigDecimal(20), Rating.GENERAL_AUDIENCES);
		itemM.addActor(2);
		
		itemM.merge(item);
		assertAll("Merge",
				() -> assertEquals(itemM.getTitle(), item.getTitle()),
				() -> assertEquals(itemM.getDescription(), item.getDescription()),
				() -> assertEquals(itemM.getActors(), item.getActors())
				);
	}

}
