package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.exceptions.InvalidDataException;

@Service
@Qualifier("Local")
public class StringServiceImpl implements StringService {
	private StringRepository dao;
	
	public StringServiceImpl(StringRepository dao) {
		this.dao = dao;
		System.out.println("Creo StringServiceImpl");
	}
	
	@Override
	public String get(Integer id) {
		return dao.load() + " en local";
	}

	@Override
	public void add(String item) {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modify(String item) {
		try {
			dao.save(item);
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(Integer id) {
		try {
			dao.save(id.toString());
		} catch (InvalidDataException e) {
			e.printStackTrace();
		}
	}

}
