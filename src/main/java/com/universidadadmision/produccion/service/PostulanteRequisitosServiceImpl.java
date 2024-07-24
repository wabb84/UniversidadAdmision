package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.entity.PostulantesRequisitos;
import com.universidadadmision.produccion.repository.PostulantesRequisitosRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulanteRequisitosServiceImpl implements PostulanteRequisitosService {

	@Autowired
	private PostulantesRequisitosRepository postulantesrequisitosrepository;
	
	@Override
	public PostulantesRequisitos save(PostulantesRequisitos postulantesrequisitos) {
		return postulantesrequisitosrepository.save( postulantesrequisitos );
	}
	
	@Override
	public List<PostulanteRequisitoDto> listapostulanterequisito(Long postulanteid){
		return postulantesrequisitosrepository.PostulanteRequisito(postulanteid);
	};
	
	@Override
	public PostulantesRequisitos read( Long postulanterequisitoid) {
		return postulantesrequisitosrepository.findById(postulanterequisitoid).orElse(null);
	};
}
