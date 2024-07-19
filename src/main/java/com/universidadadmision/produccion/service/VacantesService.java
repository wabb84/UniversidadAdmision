package com.universidadadmision.produccion.service;

import java.util.List;
import com.universidadadmision.produccion.dto.VacantesDto;
import com.universidadadmision.produccion.entity.Vacantes;

public interface VacantesService {
	public Vacantes save( Vacantes vacantes);
	public Vacantes read( Long id );
	public void delete( Long id );
	public List<VacantesDto> listartodos();
	public Vacantes findByPeriodoidAndSedeidAndCarreraid(Long idperiodo, Long idsede, Long idcarrera);
}
