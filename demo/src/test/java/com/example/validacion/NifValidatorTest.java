package com.example.validacion;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NifValidatorTest {

	@ParameterizedTest(name = "{0}")
	@CsvSource(value = { 
			"53337616H",
			"53337616h",
			"H53337616",
			"53337616",
			"th4y6hth",
			"24R",
			"97597777Z"})
	void testNifOK(String value) {
		var nifVal = new NifValidator();

		assertTrue(nifVal.nifValid(value));
	}

}
