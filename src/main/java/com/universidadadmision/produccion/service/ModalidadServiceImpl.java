package com.universidadadmision.produccion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.dto.ModalidadDto;
import com.universidadadmision.produccion.dto.ModalidadEvaluacionDto;
import com.universidadadmision.produccion.entity.Modalidad;
import com.universidadadmision.produccion.repository.ModalidadRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModalidadServiceImpl implements ModalidadService {
	@Autowired
	private ModalidadRepository modalidadrep;

	@Override
	public Modalidad save(Modalidad modalidad) {
		return modalidadrep.save(modalidad);
	}

	@Override
	public Modalidad read(Long id) {
		return modalidadrep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		modalidadrep.deleteById(id);
	}

	@Override
	public List<ModalidadDto> listartodos() {
		return modalidadrep.ListaModalidad();
	}

	@Override
	public List<Modalidad> listarxtipoingreso(Long idtipo) {
		return modalidadrep.findByTipoingresoid(idtipo);
	}

	@Override
	public List<ModalidadEvaluacionDto> listarEvaluaciones(Long id) {
		return modalidadrep.listarEvaluaciones(id);
	};

}
