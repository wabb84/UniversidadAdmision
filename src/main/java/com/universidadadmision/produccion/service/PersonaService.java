package com.universidadadmision.produccion.service;

import com.universidadadmision.produccion.entity.Persona;

public interface PersonaService {
	public Persona findByDocumento( Long id, String Numerodoc );
}
