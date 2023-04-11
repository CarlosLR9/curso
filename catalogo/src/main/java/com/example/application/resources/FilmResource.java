package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.dtos.ElementoDTO;
import com.example.domains.entities.dtos.FilmEditDTO;
import com.example.domains.entities.dtos.FilmShort;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = { "/api/pelis/v1", "/api/films" })
public class FilmResource {
	@Autowired
	private FilmService srv;

	@GetMapping
	public List<FilmShort> getAll(@RequestParam(required = false) String sort) {
		if (sort != null)
			return (List<FilmShort>) srv.getByProjection(Sort.by(sort), FilmShort.class);
		return srv.getByProjection(FilmShort.class);
	}

	@GetMapping(params = "page")
	public Page<FilmShort> getAll(Pageable pageable) {
		return srv.getByProjection(pageable, FilmShort.class);
	}

	@GetMapping(path = "/{id}")
	public FilmEditDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return FilmEditDTO.from(item.get());
	}

	@GetMapping(path = "/{id}/actores")
	@Transactional
	public List<ElementoDTO<Integer, String>> getActores(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get().getActors().stream()
				.map(o -> new ElementoDTO<>(o.getActorId(), o.getFirstName() + " " + o.getLastName())).toList();
	}

	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<ElementoDTO<Integer, String>> getCategorias(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get().getCategories().stream().map(o -> new ElementoDTO<>(o.getCategoryId(), o.getName())).toList();
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmEditDTO item)
			throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item)
			throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(FilmEditDTO.from(item));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}

}
