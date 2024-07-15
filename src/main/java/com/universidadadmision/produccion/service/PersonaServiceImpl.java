package com.universidadadmision.produccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.entity.Persona;
import com.universidadadmision.produccion.repository.PersonaRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonaService {
	@Autowired
	private PersonaRepository personarep;
	
	@Override
	public Persona findByDocumento(Long id, String Numerodoc) {
		return personarep.findByTipodocumentoidAndNrodocumento(id,Numerodoc);
	}

}
