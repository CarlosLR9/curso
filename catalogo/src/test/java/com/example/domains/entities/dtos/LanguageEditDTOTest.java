package com.example.domains.entities.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.example.domains.entities.Language;

class LanguageEditDTOTest {

	@Test
	void testFromLanguage() {
		var language = new Language(0, "Español");
		language.addFilm(1);
		language.addFilmVO(2);
		var languageDTO = LanguageEditDTO.from(language);

		assertAll("Language", 
				() -> assertEquals(LanguageEditDTO.class, languageDTO.getClass()),
				() -> assertEquals(0, languageDTO.getLanguageId()),
				() -> assertEquals("Español", languageDTO.getName()),
				() -> assertEquals(1, languageDTO.getFilms().get(0)),
				() -> assertEquals(2, languageDTO.getFilmsVO().get(0))
				);
	}

	@Test
	void testFromLanguageEditDTO() {
		var list =  new ArrayList<Integer>();
		list.add(1);
		var listVO =  new ArrayList<Integer>();
		listVO.add(2);
		var languageDTO = new LanguageEditDTO(0, "Español", list, listVO);
		var language = LanguageEditDTO.from(languageDTO);

		assertAll("LanguageEditDTO", 
				() -> assertEquals(Language.class, language.getClass()),
				() -> assertEquals(0, language.getLanguageId()), 
				() -> assertEquals("Español", language.getName()),
				() -> assertEquals(1, language.getFilms().get(0).getFilmId()),
				() -> assertEquals(2, language.getFilmsVO().get(0).getFilmId())
				);
	}

}
