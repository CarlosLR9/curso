package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
class LanguageServiceImplTest {

	@Autowired
	LanguageService srv;

	@Nested
	class Ok {
		@Test
		void testGetAll() throws DuplicateKeyException, InvalidDataException {
			assertThat(srv.getAll().size()).isGreaterThanOrEqualTo(6);
		}

		@Test
		void testGetOne() {
			assertTrue(srv.getOne(1).isPresent());
		}

		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			var item = new Language(0, "Español");
			var rslt = srv.add(item);

			assertEquals(item.getLanguageId(), rslt.getLanguageId());

			srv.deleteById(rslt.getLanguageId());
		}

		@Test
		void testModify() throws DuplicateKeyException, InvalidDataException, NotFoundException {
			var item = new Language(0, "Español");
			var rslt = srv.add(item);
			item.setName("Spanish");
			srv.modify(item);

			assertEquals("Spanish", srv.getOne(rslt.getLanguageId()).get().getName());

			srv.deleteById(rslt.getLanguageId());
		}

		@Test
		void testDelete() throws DuplicateKeyException, InvalidDataException {
			var item = new Language(0, "Español");
			var rslt = srv.add(item);
			srv.delete(rslt);
			assertThat(srv.getOne(rslt.getLanguageId()).isEmpty()).isTrue();
		}

		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			var item = new Language(0, "Español");
			var rslt = srv.add(item);
			srv.deleteById(rslt.getLanguageId());
			assertThat(srv.getOne(rslt.getLanguageId()).isEmpty()).isTrue();
		}
	}

	@Nested
	class Ko {
		@Test
		void testGetOneNotfound() {
			assertThat(srv.getOne(333).isEmpty()).isTrue();

		}

		@Test
		void testAddNull() throws DuplicateKeyException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.add(null));
		}

		@Test
		void testAddInvalid() throws DuplicateKeyException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.add(new Language(0, "")));
		}

		@Test
		void testAddDuplicated() throws DuplicateKeyException, InvalidDataException {
			assertThrows(DuplicateKeyException.class, () -> srv.add(new Language(1, "Español")));
		}

		@Test
		void testModifyNull() throws NotFoundException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.modify(null));
		}

		@Test
		void testModifyInvalid() throws NotFoundException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.modify(new Language(0, "")));
		}

		@Test
		void testModifyEmpty() throws NotFoundException, InvalidDataException {
			assertThrows(NotFoundException.class, () -> srv.modify(new Language(333, "Español")));
		}
	}

}
