package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.dto.VacantesDto;
import com.universidadadmision.produccion.entity.Vacantes;
import com.universidadadmision.produccion.repository.VacantesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacantesServiceImpl implements VacantesService {
	@Autowired
	private VacantesRepository vacantesrep;

	@Override
	public Vacantes save(Vacantes vacantes) {
		return vacantesrep.save( vacantes );
	}

	@Override
	public Vacantes read(Long id) {
		return vacantesrep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		vacantesrep.deleteById(id);
	}

	@Override
	public List<VacantesDto> listartodos() {
		return vacantesrep.ListaVacantes();
	}

	@Override
	public Vacantes findByPeriodoidAndSedeidAndCarreraid(Long idperiodo, Long idsede, Long idcarrera){
		return vacantesrep.findByPeriodoidAndSedeidAndCarreraid(idperiodo, idsede, idcarrera);
	};
}
