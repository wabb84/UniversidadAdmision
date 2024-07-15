package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.repository.PostulantesRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulantesServiceImpl implements PostulantesService {
	@Autowired
	private PostulantesRepository postulantesrep;
	
	@Override
	public Postulantes save(Postulantes postulantes) {
		return postulantesrep.save( postulantes );
	}

	@Override
	public Postulantes read(Long id) {
		return postulantesrep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		postulantesrep.deleteById(id);
	}

	@Override
	public List<PostulantesDto> listartodos() {
		return postulantesrep.ListaGrupo();
	}
}
