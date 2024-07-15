package com.universidadadmision.produccion.service;

import java.util.List;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.entity.Postulantes;

public interface PostulantesService {
	public Postulantes save( Postulantes postulantes);
	public Postulantes read( Long id );
	public void delete( Long id );
	public List<PostulantesDto> listartodos();
}
