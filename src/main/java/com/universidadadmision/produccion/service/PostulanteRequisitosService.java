package com.universidadadmision.produccion.service;

import java.util.List;

import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.entity.PostulantesRequisitos;

public interface PostulanteRequisitosService {
	public PostulantesRequisitos save( PostulantesRequisitos postulantesrequisitos);
	public PostulantesRequisitos read( Long postulanterequisitoid);
	public List<PostulanteRequisitoDto> listapostulanterequisito(Long postulanteid);
	
}
