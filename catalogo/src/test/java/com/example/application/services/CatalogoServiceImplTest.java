package com.example.application.services;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@DataJpaTest
@ComponentScan(basePackages = "com.example")
class CatalogoServiceImplTest {

	@Autowired
	CatalogoService catalogoSrv;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		catalogoSrv.novedades(Timestamp.valueOf("2019-01-01 00:00:00"));
	}

}
