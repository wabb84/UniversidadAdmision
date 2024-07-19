package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.entity.Sede;
import com.universidadadmision.produccion.repository.SedeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SedeServiceImpl implements SedeService {
	@Autowired
	private SedeRepository sederep;
	
	@Override
	public List<Sede> listarsedes(boolean estado) {
		return sederep.findByEstado(estado);
	}

}
