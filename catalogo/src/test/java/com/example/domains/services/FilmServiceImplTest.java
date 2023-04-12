package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
class FilmServiceImplTest {

	@Autowired
	FilmService srv;

	@Nested
	class Ok {
		@Test
		void testGetAll() throws DuplicateKeyException, InvalidDataException {
			assertThat(srv.getAll().size()).isGreaterThanOrEqualTo(1000);
		}

		@Test
		void testGetOne() {
			assertTrue(srv.getOne(1).isPresent());
		}

		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			var item = new Film(0, "Pinocho",
					"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", (short) 1940,
					new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80, new BigDecimal(20),
					Rating.GENERAL_AUDIENCES);
			var rslt = srv.add(item);

			assertEquals(item.getFilmId(), rslt.getFilmId());

			srv.deleteById(rslt.getFilmId());
		}

		@Test
		void testModify() throws DuplicateKeyException, InvalidDataException, NotFoundException {
			var item = new Film(0, "Pinocho",
					"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", (short) 1940,
					new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80, new BigDecimal(20),
					Rating.GENERAL_AUDIENCES);
			var rslt = srv.add(item);
			item.setTitle("Pinocchio");
			srv.modify(item);

			assertEquals("Pinocchio", srv.getOne(rslt.getFilmId()).get().getTitle());

			srv.deleteById(rslt.getFilmId());
		}

		@Test
		void testDelete() throws DuplicateKeyException, InvalidDataException {
			var item = new Film(0, "Pinocho",
					"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", (short) 1940,
					new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80, new BigDecimal(20),
					Rating.GENERAL_AUDIENCES);
			var rslt = srv.add(item);
			srv.delete(rslt);
			assertThat(srv.getOne(rslt.getFilmId()).isEmpty()).isTrue();
		}

		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			var item = new Film(0, "Pinocho",
					"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho", (short) 1940,
					new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80, new BigDecimal(20),
					Rating.GENERAL_AUDIENCES);
			var rslt = srv.add(item);
			srv.deleteById(rslt.getFilmId());
			assertThat(srv.getOne(rslt.getFilmId()).isEmpty()).isTrue();
		}
	}

	@Nested
	class Ko {
		@Test
		void testGetOneNotfound() {
			assertThat(srv.getOne(3333).isEmpty()).isTrue();

		}

		@Test
		void testAddNull() throws DuplicateKeyException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.add(null));
		}

		@Test
		void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
			assertThrows(InvalidDataException.class,
					() -> srv.add(new Film(0, "",
							"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho",
							(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80,
							new BigDecimal(20), Rating.GENERAL_AUDIENCES)));
		}

		@Test
		void testAddDuplicated() throws DuplicateKeyException, InvalidDataException {
			assertThrows(DuplicateKeyException.class,
					() -> srv.add(new Film(1, "Pinocho",
							"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho",
							(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80,
							new BigDecimal(20), Rating.GENERAL_AUDIENCES)));
		}

		@Test
		void testModifyNull() throws NotFoundException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.modify(null));
		}

		@Test
		void testModifyInvalid() throws NotFoundException, InvalidDataException {
			assertThrows(InvalidDataException.class,
					() -> srv.modify(new Film(0, "",
							"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho",
							(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80,
							new BigDecimal(20), Rating.GENERAL_AUDIENCES)));
		}

		@Test
		void testModifyEmpty() throws NotFoundException, InvalidDataException {
			assertThrows(NotFoundException.class,
					() -> srv.modify(new Film(3333, "Pinocho",
							"Un anciano llamado Geppetto fabrica una marioneta de madera a la que llama Pinocho",
							(short) 1940, new Language(1), new Language(2), (byte) 2, new BigDecimal(2), 80,
							new BigDecimal(20), Rating.GENERAL_AUDIENCES)));
		}
	}

}
