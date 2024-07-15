package com.universidadadmision.produccion;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversidadadmisionApplication {

	public static void main(String[] args) {
		TimeZone tz = TimeZone.getTimeZone("America/Lima");
		TimeZone.setDefault(tz);
		SpringApplication.run(UniversidadadmisionApplication.class, args);
	}

}
