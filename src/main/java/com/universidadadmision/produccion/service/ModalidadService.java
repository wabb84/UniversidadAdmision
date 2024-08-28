package com.universidadadmision.produccion.service;

import java.util.List;

import com.universidadadmision.produccion.dto.ModalidadDto;
import com.universidadadmision.produccion.dto.ModalidadEvaluacionDto;
import com.universidadadmision.produccion.entity.Modalidad;

public interface ModalidadService {
	public Modalidad save( Modalidad modalidad );
	public Modalidad read( Long id );
	public void delete( Long id );
	public List<ModalidadDto> listartodos();
	public List<Modalidad> listarxtipoingreso(Long idtipo);
	public List<ModalidadEvaluacionDto> listarEvaluaciones(Long id);
}
