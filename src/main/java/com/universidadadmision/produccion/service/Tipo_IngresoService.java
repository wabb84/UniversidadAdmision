package com.universidadadmision.produccion.service;

import java.util.List;

import com.universidadadmision.produccion.dto.Tipo_IngresoDto;
import com.universidadadmision.produccion.entity.Tipo_Ingreso;

public interface Tipo_IngresoService {
	public Tipo_Ingreso save( Tipo_Ingreso tipo_ingreso );
	public Tipo_Ingreso read( Long id );
	public Tipo_IngresoDto readdto( Long id );
	public void delete( Long id );
	public List<Tipo_IngresoDto> listartodos();
}
