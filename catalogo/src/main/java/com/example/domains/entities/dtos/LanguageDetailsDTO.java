package com.example.domains.entities.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LanguageDetailsDTO {
	@JsonProperty("id")
	private int languageId;
	@JsonProperty("nombre")
	private String name;
	private List<String> films = new ArrayList<String>();
	private List<String> filmsVO = new ArrayList<String>();
	
	public static LanguageDetailsDTO from(Language source) {
		return new LanguageDetailsDTO(
				source.getLanguageId(), 
				source.getName(), 
				source.getFilms().stream().map(item -> item.getTitle())
				.collect(Collectors.toList()),
				source.getFilmsVO().stream().map(item -> item.getTitle())
				.collect(Collectors.toList())
				);
	}

}
