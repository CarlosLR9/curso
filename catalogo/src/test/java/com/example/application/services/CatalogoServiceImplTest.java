package com.example.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
class CatalogoServiceImplTest {

	@Autowired
	CatalogoService catalogoSrv;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Disabled
	void test() {
		assertEquals(
				"NovedadesDTO(films=[], actors=[ActorEditDTO(actorId=204, firstName=PEPITO, lastName=GRILLO, films=[])], categories=[], languages=[])",
				catalogoSrv.novedades(Timestamp.valueOf("2019-01-01 00:00:01")).toString());
	}

}
