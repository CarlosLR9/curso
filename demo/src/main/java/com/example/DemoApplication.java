package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import com.example.ioc.StringRepository;
//import com.example.ioc.StringRepositoryImpl;
import com.example.ioc.StringService;
//import com.example.ioc.StringServiceImpl;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private StringService srv;
	
	public void run(String... args) throws Exception{
		System.out.println("Aplicacion arrancada");
		

//		StringRepository dao = new StringRepositoryImpl();
//		var srv = new StringServiceImpl(dao);
		System.out.println(srv.get(1));
	}
}
