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
public class CategoryDetailsDTO {
	@JsonProperty("id")
	private int categoryId;
	@JsonProperty("nombre")
	private String name;
	private List<String> films = new ArrayList<String>();
	
	public static CategoryDetailsDTO from(Category source) {
		return new CategoryDetailsDTO(
				source.getCategoryId(), 
				source.getName(), 
				source.getFilms().stream().map(item -> item.getTitle())
				.collect(Collectors.toList())
				);
	}
	
}
