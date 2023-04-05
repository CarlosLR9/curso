package com.example.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@SpringBootTest
class ActorServiceImplTest {

	@Autowired
	ActorService srv;

	@Test
	void testGetAll() throws DuplicateKeyException, InvalidDataException {
		assertThat(srv.getAll().size()).isGreaterThanOrEqualTo(200);
	}

	@Test
	void testGetOne() {
		assertEquals("Actor [actorId=1, firstName=PENELOPE, lastName=GUINES, "
				+ "lastUpdate=2006-02-15 04:34:33.0]", srv.getOne(1).get().toString());
	}
	
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
		assertThrows(InvalidDataException.class, () -> srv.add(new Actor(0, "", "grillo")));
	}
	
	@Test
	void testAddDuplicated() throws DuplicateKeyException, InvalidDataException {
		assertThrows(DuplicateKeyException.class, () -> srv.add(new Actor(1, "PENELOPEEE", "GUINESSSS")));
	}
	
	@Test 
	void testAdd() throws DuplicateKeyException, InvalidDataException {
		var item = new Actor(0, "JIMINY", "CRICKET");
		var rslt = srv.add(item);
		
		assertEquals(item.getActorId(), rslt.getActorId());
		
		srv.deleteById(rslt.getActorId());
	}

	@Test
	void testModifyNull() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(null));
	}
	
	@Test
	void testModifyInvalid() throws NotFoundException, InvalidDataException {
		assertThrows(InvalidDataException.class, () -> srv.modify(new Actor(204, "", "grillo")));
	}
	
	@Test
	void testModifyEmpty() throws NotFoundException, InvalidDataException {
		assertThrows(NotFoundException.class, () -> srv.modify(new Actor(3333, "JIMINY", "CRICKET")));
	}
	
	@Test
	void testModify() throws DuplicateKeyException, InvalidDataException, NotFoundException {
		var item = new Actor(0, "JIMINY", "CRICKET");
		var rslt = srv.add(item);
		item.setLastName("GRILLO");
		srv.modify(item);
		
		assertEquals("GRILLO", srv.getOne(rslt.getActorId()).get().getLastName());
		
		srv.deleteById(rslt.getActorId());
	}

		@Test
		void testDelete() throws DuplicateKeyException, InvalidDataException {
			var item = new Actor(0, "JIMINY", "CRICKET");
			item.addFilm(1);
			var rslt = srv.add(item);
			srv.delete(rslt);
			assertThat(srv.getOne(rslt.getActorId()).isEmpty()).isTrue();
		}
	
		@Test
		void testDeleteById() throws DuplicateKeyException, InvalidDataException {
			var item = new Actor(0, "JIMINY", "CRICKET");
			item.addFilm(1);
			var rslt = srv.add(item);
			srv.deleteById(rslt.getActorId());
			assertThat(srv.getOne(rslt.getActorId()).isEmpty()).isTrue();
		}

}
