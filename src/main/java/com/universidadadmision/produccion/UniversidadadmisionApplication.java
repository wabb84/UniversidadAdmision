package com.universidadadmision.produccion;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:basedatos.properties")
public class UniversidadadmisionApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UniversidadadmisionApplication.class);
	}

	public static void main(String[] args) {
		TimeZone tz = TimeZone.getTimeZone("America/Lima");
		TimeZone.setDefault(tz);
		SpringApplication.run(UniversidadadmisionApplication.class, args);
	}

}
