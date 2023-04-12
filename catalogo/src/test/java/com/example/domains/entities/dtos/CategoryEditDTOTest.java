package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Category;

class CategoryEditDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFromCategory() {
		var category = new Category(0, "Animacion");
		category.addFilm(1);
		var categoryDTO = CategoryEditDTO.from(category);

		assertAll("Category", 
				() -> assertEquals(CategoryEditDTO.class, categoryDTO.getClass()),
				() -> assertEquals(0, categoryDTO.getCategoryId()), 
				() -> assertEquals("Animacion", categoryDTO.getName()),
				() -> assertEquals(1, categoryDTO.getFilms().get(0))
				);
	}

	@Test
	void testFromCategoryEditDTO() {
		var list =  new ArrayList<Integer>();
		list.add(1);
		var categoryEditDTO = new CategoryEditDTO(0, "Animacion", list);
		var category = CategoryEditDTO.from(categoryEditDTO);

		assertAll("CategoryEditDTO", 
				() -> assertEquals(Category.class, category.getClass()),
				() -> assertEquals(0, category.getCategoryId()), 
				() -> assertEquals("Animacion", category.getName()),
				() -> assertEquals(1, category.getFilms().get(0).getFilmId())
				);
	}

}
