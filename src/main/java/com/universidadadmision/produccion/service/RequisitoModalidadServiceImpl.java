package com.universidadadmision.produccion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.dto.RequisitoModalidadDto;
import com.universidadadmision.produccion.entity.RequisitosModalidad;
import com.universidadadmision.produccion.repository.RequisitosModalidadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequisitoModalidadServiceImpl implements RequisitoModalidadService {
	@Autowired
	private RequisitosModalidadRepository requisitomodalidadrep;
	
	@Override
	public List<RequisitoModalidadDto> listarxrequisitomodalidad(Long modalidadid) {
		return requisitomodalidadrep.ListadoRequisitosModadlida(modalidadid);
	}

	@Override
	public RequisitosModalidad save(RequisitosModalidad reqmod) {
		return requisitomodalidadrep.save( reqmod );
	}

	@Override
	public RequisitosModalidad read(Long id) {
		return requisitomodalidadrep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		requisitomodalidadrep.deleteById(id);
	}
}
