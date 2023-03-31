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
public class ActorEditDTO {
	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellidos")
	private String lastName;
	private List<Integer> films = new ArrayList<Integer>();
	
	public static ActorEditDTO from(Actor source) {
		return new ActorEditDTO(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName(),
				source.getFilms().stream().map(item -> item.getFilmId())
				.collect(Collectors.toList())
				);
	}

	public static Actor from(ActorEditDTO source) {
		var rslt = new Actor(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
		source.getFilms().stream().forEach(item -> rslt.addFilm(item));
		return rslt;
	}
}