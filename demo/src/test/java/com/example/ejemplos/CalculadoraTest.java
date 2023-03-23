package com.example.ejemplos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculadoraTest {
	Calculadora calc;
	
	@BeforeEach
	void setUp() throws Exception {
		calc = new Calculadora();
	}

	@Nested
	@DisplayName("Pruebas del metodo suma")
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class Suma{
		@Nested
		class OK{
			@Test
			void testSuma() {
				var rslt = calc.suma(2, 2);
				
				assertEquals(4, rslt);
			}
			
			@Test
			void testSumaPositivoNegativo() {
				var rslt = calc.suma(3, -1);
				
				assertEquals(2, rslt);
			}

			@Test
			void testSumaNegativoPositivo() {
				var rslt = calc.suma(-1, 5);
				
				assertEquals(4, rslt);
			}
			
			@Test
			void testSumaNegativoNegativo() {
				var rslt = calc.suma(-1, -5);
				
				assertEquals(-6, rslt);
			}
			
			@Test
			void testSumaDecimales() {
				var rslt = calc.suma(0.1, 0.2);
				
				assertEquals(0.3, rslt);
				//assertEquals(0.3, rslt, 0.01);
			}
			
			@Test
			void testSumaDecimalesPositivoNegativo() {
				var rslt = calc.suma(1, -0.9);
				
				assertEquals(0.1, rslt);
				//assertEquals(0.1, rslt, 0.01);
			}
			
			void testSumaMultiple() {
				assertEquals(2, calc.suma(1, 1));
				assertEquals(0, calc.suma(-1, 1));
				assertEquals(0, calc.suma(1, -1));
				assertEquals(-2, calc.suma(-1, -1));
				assertEquals(0, calc.suma(0, 0));
			}
			
			@ParameterizedTest(name = "{0} + {1} = {2}")
			@CsvSource(value = {"1,1,2", "0.1,0.2,0.3", "0,0,0", "-1,1,1"})
			void testSumasOK(double op1, double op2, double rslt) {
				assertEquals(rslt, calc.suma(op1, op2));
			}
			
			
		}
		
		@Nested
		class KO{
			
		}
	}
	
	@Nested
	class Dividir{
		@Nested
		class OK{
			@Test
			void testDividir() {
				var calc = new Calculadora();
				
				var rslt = calc.divide(1, 2);
				
				assertEquals(0.5, rslt);
				}
			
//			@Test
//			void testDividirPorCero() {
//				var calc = new Calculadora();
//				
//				var rslt = calc.divide(1, 0.0);
//				
//				assertEquals(Double.POSITIVE_INFINITY, rslt);
//				}
		}
		
		@Nested
		class KO{
			@Test
			void testDividirPor0() {
				var calc = new Calculadora();
				
				assertThrows(ArithmeticException.class, () -> calc.divide(1, 0));
				}
		}
	}
}
