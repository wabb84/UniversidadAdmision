package com.universidadadmision.produccion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesDto;
import com.universidadadmision.produccion.entity.PostulantesEvaluacion;
import com.universidadadmision.produccion.repository.PostulantesEvaluacionRepository;

@Service
public class PostulantesEvaluacionServiceImpl implements PostulantesEvaluacionService {

    @Autowired
    private PostulantesEvaluacionRepository postulanteEvaluacionRepository;

    @Override
    public List<PostulantesEvaluacion> findAll() {
        return postulanteEvaluacionRepository.findAll();
    }

    @Override
    public Optional<PostulantesEvaluacion> findById(Long id) {
        return postulanteEvaluacionRepository.findById(id);
    }

    @Override
    public PostulantesEvaluacion save(PostulantesEvaluacion postulanteEvaluacion) {
        return postulanteEvaluacionRepository.save(postulanteEvaluacion);
    }

    @Override
    public void deleteById(Long id) {
        postulanteEvaluacionRepository.deleteById(id);
    }

    @Override
    public List<PostulantesEvaluacionDto> listaPostulantesEvaluacion(Long grupoId) {
       return postulanteEvaluacionRepository.listaPostulantesEvaluacion(grupoId);
    }

    @Override
    public List<PostulantesEvaluacionesDto> findByPostulanteId(Long postulanteId) throws Exception {
        return postulanteEvaluacionRepository.findByPostulanteId(postulanteId);
    }
}