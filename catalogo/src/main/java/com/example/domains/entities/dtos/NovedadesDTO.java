package com.example.domains.entities.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<FilmEditDTO> films;
	private List<ActorEditDTO> actors;
	private List<CategoryEditDTO> categories;
	private List<LanguageEditDTO> languages;
	
}