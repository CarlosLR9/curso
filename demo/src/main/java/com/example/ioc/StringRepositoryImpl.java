package com.example.ioc;

// import org.springframework.stereotype.Component; //fuera de uso
import org.springframework.stereotype.Repository;

// @Component
@Repository
public class StringRepositoryImpl implements StringRepository {

	@Override
	public String load() {
		return "Soy el StringRepositoryImpl";
	}

	@Override
	public void save(String item) {
		System.out.println("Guardo el item: " + item);

	}

}
