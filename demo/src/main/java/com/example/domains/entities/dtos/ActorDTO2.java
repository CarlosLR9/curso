package com.example.domains.entities.dtos;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ActorDTO2 {
	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellidos")
	private String lastName;
	
	public static ActorDTO2 from(Actor source) {
		return new ActorDTO2(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}

	public static Actor from(ActorDTO2 source) {
		var rslt = new Actor(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
		return rslt;
	}
}