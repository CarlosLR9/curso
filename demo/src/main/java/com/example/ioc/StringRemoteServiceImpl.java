package com.example.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
@Qualifier("Remoto")
public class StringRemoteServiceImpl implements StringService {
	@Autowired
	private StringRepository dao;
	
	@Override
	public String get(Integer id) {
		return dao.load() + " en remoto";
	}

	@Override
	public void add(String item) throws NotFoundException {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			throw new NotFoundException("No encontrado", e);
		}
	}

	@Override
	public void modify(String item) throws InvalidDataException {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Integer id) throws InvalidDataException {
		try {
			dao.save(id.toString());
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

}