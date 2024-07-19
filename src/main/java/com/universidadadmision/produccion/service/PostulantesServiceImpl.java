package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.repository.PostulantesRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulantesServiceImpl implements PostulantesService {
	@Autowired
	private PostulantesRepository postulantesrep;
	
	@Transactional
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
	
	@Override
	public List<Postulantes> findpostulantevacante(Long idpersona, Long idvacante){
		return postulantesrep.findByPersonaidAndVacanteid(idpersona,idvacante);
	};
	
	@Transactional
	@Override
	public GeneralDto generacodigo(Long periodoid) {
		return postulantesrep.GeneraCodigoPostulante(periodoid);
	}
	
	@Override
	public List<PostulanteNotasDto> postulantenotaso(Long periodoid){
		return postulantesrep.PostulanteNotasO(periodoid);
	};
	
	@Override
	@Transactional
	public void postulantenotasi(List<PostulanteNotasIDtoR> postulantes){
		for (PostulanteNotasIDtoR postulante : postulantes) {
			Postulantes postulanteexis = postulantesrep.findById(postulante.getId()).orElse(null);
			
			postulanteexis.setNota(postulante.getNota());
			postulanteexis.setEstado_postulante("I");
			postulantesrep.save(postulanteexis);
			//System.out.println(postulante.getCodigo());
		}
		
	};
}
