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
public class LanguageEditDTO {
	@JsonProperty("id")
	private int languageId;
	@JsonProperty("nombre")
	private String name;
	private List<Integer> films = new ArrayList<Integer>();
	private List<Integer> filmsVO = new ArrayList<Integer>();
	
	public static LanguageEditDTO from(Language source) {
		return new LanguageEditDTO(
				source.getLanguageId(), 
				source.getName(), 
				source.getFilms().stream().map(item -> item.getFilmId())
				.collect(Collectors.toList()),
				source.getFilmsVO().stream().map(item -> item.getFilmId())
				.collect(Collectors.toList())
				);
	}

	public static Language from(LanguageEditDTO source) {
		var rslt = new Language(
				source.getLanguageId(), 
				source.getName()
				);
		source.getFilms().stream().forEach(item -> rslt.addFilm(item));
		source.getFilmsVO().stream().forEach(item -> rslt.addFilmVO(item));
		return rslt;
	}

}
