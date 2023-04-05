package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;

class CategoryEditDTOTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFromCategory() {
		var categoryDTO = CategoryEditDTO.from(new Category(0, "Animacion"));

		assertAll("Category", 
				() -> assertEquals(CategoryEditDTO.class, categoryDTO.getClass()),
				() -> assertEquals(0, categoryDTO.getCategoryId()), 
				() -> assertEquals("Animacion", categoryDTO.getName()));
	}

	@Test
	void testFromCategoryEditDTO() {
		var category = CategoryEditDTO.from(new CategoryEditDTO(0, "Animacion", new ArrayList<>()));

		assertAll("CategoryEditDTO", 
				() -> assertEquals(Category.class, category.getClass()),
				() -> assertEquals(0, category.getCategoryId()), 
				() -> assertEquals("Animacion", category.getName()));
	}

}
