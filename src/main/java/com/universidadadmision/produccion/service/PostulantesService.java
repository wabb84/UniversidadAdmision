package com.universidadadmision.produccion.service;

import java.util.List;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.entity.Postulantes;

public interface PostulantesService {
	public Postulantes save( Postulantes postulantes);
	public Postulantes read( Long id );
	public void delete( Long id );
	public List<PostulantesDto> listartodos();
	public List<Postulantes> findpostulantevacante(Long idpersona, Long idvacante);
	public GeneralDto generacodigo(Long periodoid);
	public List<PostulanteNotasDto> postulantenotaso(Long periodoid);
	public void postulantenotasi(List<PostulanteNotasIDtoR> postulantes);
}
