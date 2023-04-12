package com.example.domains.entities.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ActorDetailsDTO {
	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellidos")
	private String lastName;
	private List<String> films = new ArrayList<String>();
	
	public static ActorDetailsDTO from(Actor source) {
		return new ActorDetailsDTO(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName(),
				source.getFilms().stream().map(item -> item.getTitle())
				.collect(Collectors.toList())
				);
	}
}