package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contract.repositories.ActorRepository;
import com.example.domains.entities.Actor;

//import com.example.ioc.EjemplosIoC;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;

	@Override
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
		dao.findTop5ByFirstNameStartingWith("p", Sort.by("LastName").descending())
			.forEach(System.out::println);
		dao.findTop5ByFirstNameStartingWith("p", Sort.by("LastName"))
		.forEach(System.out::println);
	}
}
