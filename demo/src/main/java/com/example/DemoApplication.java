package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
//import com.example.ioc.EjemplosIoC;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
//		(new EjemplosIoC()).run();
		
//		var actors = new Actor(0, "Pepito", "Grillo");
//		dao.save(actors);
//		dao.deleteById(205);
//		var item = dao.findById(204);
//		if(item.isPresent()) {
//			var actor = item.get();
//			actor.setLastName(actor.getLastName().toUpperCase());
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//			dao.findAll().forEach(System.out::println);
//		} else {
//			System.out.println("Actor no encontrado");
//		}
//		dao.findTop5ByFirstNameStartingWithOrderByLastNameDesc("p")
//			.forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("p", Sort.by("LastName").descending())
//			.forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWith("p", Sort.by("LastName"))
//		.forEach(System.out::println);
//		dao.findConJPQL().forEach(System.out::println);
//		dao.findConJPQL(5).forEach(System.out::println);
//		dao.findConSQL(5).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 5))
//			.forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThan(root.get("actorId"), 200))
//			.forEach(System.out::println);
//		var item = dao.findById(1);
//		if (item.isPresent()) {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors().forEach(o -> System.out.println(o.getFilm().getTitle()));
//		} else {
//			System.out.println("Actor no encontrado");
//		}
//		var rslt = dao.findAll(PageRequest.of(1, 20, Sort.by("actorId")));
//		rslt.getContent().stream().map(item -> ActorDTO.from(item)).forEach(System.out::println);
//		dao.findByActorIdNotNull().forEach(System.out::println);
//		dao.findByActorIdNotNull().forEach(item->System.out.println(item.getActorId() + " " + item.getNombre()));
//		dao.findAllBy(ActorShort.class).forEach(item->System.out.println(item.getActorId() + " " + item.getNombre()));
//		dao.findAllBy(ActorDTO.class).forEach(System.out::println);
//		var actor = new Actor(0, "4", "d");
////		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
////		var err = validator.validate(actor);
////		if(err.size() > 0) {
////			err.forEach(e -> System.out.println(e.getPropertyPath() + ": " + e.getMessage()));
////		} else 
////			dao.save(actor);
//		if(actor.isInvalid()) {
//			System.out.println(actor.getErrorsMessage());
//		} else 
//			dao.save(actor);
//		ObjectMapper objectMapper = new ObjectMapper();
//		dao.findAllBy(ActorDTO.class).stream().map(
//				item -> {
//					try {
//						return objectMapper.writeValueAsString(item);
//					} catch (JsonProcessingException e) {
//						return "";
//					}
//				}).forEach(System.out::println);
	}
}
