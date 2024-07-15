package com.universidadadmision.produccion.service;

import java.util.List;

import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.entity.Grupo;

public interface GrupoService {
	public Grupo save( Grupo grupo);
	public Grupo read( Long id );
	public void delete( Long id );
	public List<GrupoDto> listartodos();
}
