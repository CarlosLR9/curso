package com.example.validacion;

public class NifValid {
	
	public boolean nifValid(String value) {
		value = value.toUpperCase();
		return !value.matches("^\\d{8}[A-Z]$") ? false 
				: "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(value.substring(0, value.length() - 1)) % 23)
				== value.charAt(value.length() - 1);
	}
}
