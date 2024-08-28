package com.universidadadmision.produccion.service;

import java.util.List;
import java.util.Optional;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesDto;
import com.universidadadmision.produccion.entity.PostulantesEvaluacion;

public interface PostulantesEvaluacionService {
    List<PostulantesEvaluacion> findAll();

    Optional<PostulantesEvaluacion> findById(Long id);

    PostulantesEvaluacion save(PostulantesEvaluacion postulanteEvaluacion);

    void deleteById(Long id);

    List<PostulantesEvaluacionDto> listaPostulantesEvaluacion(Long grupoId);

    List<PostulantesEvaluacionesDto> findByPostulanteId(Long postulanteId) throws Exception;
}