package com.example.ioc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//import lombok.Builder;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("rango")
//@Builder ya se buildea desde el .properties
public class Rango {
	private int min;
	private int max;
}