package com.example.domains.entities.dtos;

import com.example.domains.entities.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CategoryDTO {
	@JsonProperty("id")
	private int categoryId;
	@JsonProperty("nombre")
	private String name;
	
	public static CategoryDTO from(Category source) {
		return new CategoryDTO(
				source.getCategoryId(), 
				source.getName()
				);
	}

	public static Category from(CategoryDTO source) {
		var rslt = new Category(
				source.getCategoryId(), 
				source.getName()
				);
		return rslt;
	}
	
}
