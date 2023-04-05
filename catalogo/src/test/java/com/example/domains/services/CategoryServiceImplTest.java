package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Category;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
class CategoryServiceImplTest {

	@Autowired
	CategoryService srv;

	@Nested
	class Ok {
		@Test
		void testGetAll() throws DuplicateKeyException, InvalidDataException {
			assertThat(srv.getAll().size()).isGreaterThanOrEqualTo(16);
		}

		@Test
		void testGetOne() {
			assertTrue(srv.getOne(1).isPresent());
		}

		@Test
		void testAdd() throws DuplicateKeyException, InvalidDataException {
			var item = new Category(0, "Animacion");
			var rslt = srv.add(item);

			assertEquals(item.getCategoryId(), rslt.getCategoryId());

			srv.deleteById(rslt.getCategoryId());
		}

		@Test
		void testModify() throws DuplicateKeyException, InvalidDataException, NotFoundException {
			var item = new Category(0, "Animacion");
			var rslt = srv.add(item);
			item.setName("Dibujos");
			srv.modify(item);

			assertEquals("Dibujos", srv.getOne(rslt.getCategoryId()).get().getName());

			srv.deleteById(rslt.getCategoryId());
		}

		@Test
		void testDelete() throws DuplicateKeyException, InvalidDataException {
			var item = new Category(0, "Animacion");
			var rslt = srv.add(item);
			srv.delete(rslt);
			assertThat(srv.getOne(rslt.getCategoryId()).isEmpty()).isTrue();
		}

		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			var item = new Category(0, "Animacion");
			var rslt = srv.add(item);
			srv.deleteById(rslt.getCategoryId());
			assertThat(srv.getOne(rslt.getCategoryId()).isEmpty()).isTrue();
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
			assertThrows(InvalidDataException.class, () -> srv.add(new Category(0, "")));
		}

		@Test
		void testAddDuplicated() throws DuplicateKeyException, InvalidDataException {
			assertThrows(DuplicateKeyException.class, () -> srv.add(new Category(1, "Animacion")));
		}

		@Test
		void testModifyNull() throws NotFoundException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.modify(null));
		}

		@Test
		void testModifyInvalid() throws NotFoundException, InvalidDataException {
			assertThrows(InvalidDataException.class, () -> srv.modify(new Category(0, "")));
		}

		@Test
		void testModifyEmpty() throws NotFoundException, InvalidDataException {
			assertThrows(NotFoundException.class, () -> srv.modify(new Category(333, "Animacion")));
		}
	}

}
