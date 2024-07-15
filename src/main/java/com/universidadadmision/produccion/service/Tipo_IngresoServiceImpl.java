package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.Tipo_IngresoDto;
import com.universidadadmision.produccion.entity.Tipo_Ingreso;
import com.universidadadmision.produccion.repository.Tipo_IngresoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tipo_IngresoServiceImpl implements Tipo_IngresoService {
	
	@Autowired
	private Tipo_IngresoRepository tipo_ingresorep;

	@Override
	public Tipo_Ingreso save(Tipo_Ingreso tipo_ingreso) {
		return tipo_ingresorep.save( tipo_ingreso );
	}

	@Override
	public Tipo_Ingreso read(Long id) {
		return tipo_ingresorep.findById(id).orElse(null);
	}

	@Override
	public Tipo_IngresoDto readdto(Long id) {
		return tipo_ingresorep.findTipo_IngresoByid(id).orElse(null); 
	}
	
	@Override
	public void delete(Long id) {
		tipo_ingresorep.deleteById(id);
	}
	
	@Override
	public List<Tipo_IngresoDto> listartodos(){
		return tipo_ingresorep.findAllProjectedBy();
	};
}