package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.entity.Grupo;
import com.universidadadmision.produccion.repository.GrupoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrupoServiceImpl implements GrupoService {
	@Autowired
	private GrupoRepository gruporep;
	
	@Override
	public Grupo save(Grupo grupo) {
		return gruporep.save( grupo );
	}

	@Override
	public Grupo read(Long id) {
		return gruporep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		gruporep.deleteById(id);
	}

	@Override
	public List<GrupoDto> listartodos() {
		return gruporep.ListaGrupo();
	}
	
	@Override
	public List<Grupo> listarxperiodo(Long periodoid, Long tipoingresoid){
		return gruporep.findByPeriodoidAndTipoingresoid(periodoid,tipoingresoid);
	};
}
