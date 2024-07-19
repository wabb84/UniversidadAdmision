package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.entity.Periodo;
import com.universidadadmision.produccion.repository.PeriodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeriodoServiceImpl implements PeriodoService {
	@Autowired
	private PeriodoRepository periodorep;
	
	@Override
	public List<Periodo> listarperiodos(boolean estado, Long tipoperiodo) {
		return periodorep.findByEstadoAndPeriodotipo(estado,tipoperiodo);
	}
	
	@Override
	public Periodo findByid(Long periodoid) {
		return periodorep.findById(periodoid).orElse(null);
	};

}
