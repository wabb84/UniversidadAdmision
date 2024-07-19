package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.entity.Carrera;
import com.universidadadmision.produccion.repository.CarreraRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarreraServiceImpl implements CarreraService {
	@Autowired
	private CarreraRepository carrerarep;
	
	@Override
	public List<Carrera> listarcarreras(boolean estado) {
		return carrerarep.findByEstado(estado);
	}

}
