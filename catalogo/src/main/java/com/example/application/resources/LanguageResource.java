package com.example.application.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.LanguageEditDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = { "/api/lenguajes/v1", "/api/languages" })
public class LanguageResource {
	@Autowired
	private LanguageService srv;

	@GetMapping
	public List<LanguageEditDTO> getAll() throws NotFoundException {
		var list = srv.getAll();
		if (list.isEmpty())
			throw new NotFoundException();
		var listDTO = new ArrayList<LanguageEditDTO>();
		list.forEach(item -> listDTO.add(LanguageEditDTO.from(item)));
		return listDTO;
	}

	@GetMapping(path = "/{id}")
	public LanguageEditDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return LanguageEditDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<ElementoDTO<Integer, String>> getPelis(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilms().stream()
				.map(o -> new ElementoDTO<>(o.getFilmId(), o.getTitle()))
				.toList();
	}
	
	@GetMapping(path = "/{id}/pelis/vo")
	@Transactional
	public List<ElementoDTO<Integer, String>> getPelisVO(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmsVO().stream()
				.map(o -> new ElementoDTO<>(o.getFilmId(), o.getTitle()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody LanguageEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(LanguageEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody LanguageEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(id != item.getLanguageId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(LanguageEditDTO.from(item));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}
