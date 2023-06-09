package com.example.application.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.dtos.ActorEditDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.CategoryEditDTO;
import com.example.domains.entities.dtos.LanguageEditDTO;
import com.example.domains.entities.dtos.NovedadesDTO;

@Service
public class CatalogoServiceImpl implements CatalogoService {
	@Autowired
	private FilmService filmSrv;
	@Autowired
	private ActorService artorSrv;
	@Autowired
	private CategoryService categorySrv;
	@Autowired
	private LanguageService languageSrv;

	@Override
	public NovedadesDTO novedades(Timestamp fecha) {
		if (fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item -> FilmEditDTO.from(item)).toList(),
				artorSrv.novedades(fecha).stream().map(item -> ActorEditDTO.from(item)).toList(),
				categorySrv.novedades(fecha).stream().map(item -> CategoryEditDTO.from(item)).toList(),
				languageSrv.novedades(fecha).stream().map(item -> LanguageEditDTO.from(item)).toList());
	}

}