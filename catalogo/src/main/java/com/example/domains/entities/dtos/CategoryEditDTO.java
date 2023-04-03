package com.example.domains.entities.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CategoryEditDTO {
	@JsonProperty("id")
	private int categoryId;
	@JsonProperty("nombre")
	private String name;
	private List<Integer> films = new ArrayList<Integer>();
	
	public static CategoryEditDTO from(Category source) {
		return new CategoryEditDTO(
				source.getCategoryId(), 
				source.getName(), 
				source.getFilms().stream().map(item -> item.getFilmId())
				.collect(Collectors.toList())
				);
	}

	public static Category from(CategoryEditDTO source) {
		var rslt = new Category(
				source.getCategoryId(), 
				source.getName()
				);
		source.getFilms().stream().forEach(item -> rslt.addFilm(item));
		return rslt;
	}
	
}
