package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;

class ActorEditDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFromActor() {
		var actorDTO = ActorEditDTO.from(new Actor(0, "PEPITO", "GRILLO"));
		
		assertAll("Actor",
				() -> assertEquals(ActorEditDTO.class, actorDTO.getClass()),
				() -> assertEquals(0, actorDTO.getActorId()),
				() -> assertEquals("PEPITO", actorDTO.getFirstName()),
				() -> assertEquals("GRILLO", actorDTO.getLastName()));
	}
	
	@Test
	void testFromActorEditDTO() {
		var actor = ActorEditDTO.from(new ActorEditDTO(0, "PEPITO", "GRILLO", new ArrayList<>()));
		
		assertAll("ActorEditDTO",
				() -> assertEquals(Actor.class, actor.getClass()),
				() -> assertEquals(0, actor.getActorId()),
				() -> assertEquals("PEPITO", actor.getFirstName()),
				() -> assertEquals("GRILLO", actor.getLastName()));
	}

}
