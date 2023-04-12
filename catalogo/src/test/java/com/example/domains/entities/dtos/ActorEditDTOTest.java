package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;

class ActorEditDTOTest {

	@Test
	void testFromActor() {
		var actor = new Actor(0, "PEPITO", "GRILLO");
		actor.addFilm(1);
		var actorDTO = ActorEditDTO.from(actor);
		
		assertAll("Actor",
				() -> assertEquals(ActorEditDTO.class, actorDTO.getClass()),
				() -> assertEquals(0, actorDTO.getActorId()),
				() -> assertEquals("PEPITO", actorDTO.getFirstName()),
				() -> assertEquals("GRILLO", actorDTO.getLastName()),
				() -> assertEquals(1, actorDTO.getFilms().get(0))
				);
	}
	
	@Test
	void testFromActorEditDTO() {
		var list =  new ArrayList<Integer>();
		list.add(1);
		var actorDTO = new ActorEditDTO(0, "PEPITO", "GRILLO", list);
		var actor = ActorEditDTO.from(actorDTO);
		
		assertAll("ActorEditDTO",
				() -> assertEquals(Actor.class, actor.getClass()),
				() -> assertEquals(0, actor.getActorId()),
				() -> assertEquals("PEPITO", actor.getFirstName()),
				() -> assertEquals("GRILLO", actor.getLastName()),
				() -> assertEquals(1, actor.getFilms().get(0))
				);
	}

}
