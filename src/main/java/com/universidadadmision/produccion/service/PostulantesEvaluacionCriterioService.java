package com.universidadadmision.produccion.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionesCriterioDto;
import com.universidadadmision.produccion.entity.PostulantesEvaluacionCriterio;

public interface PostulantesEvaluacionCriterioService {
    List<PostulantesEvaluacionCriterio> findAll();

    Optional<PostulantesEvaluacionCriterio> findById(Long id);

    PostulantesEvaluacionCriterio save(PostulantesEvaluacionCriterio postulanteEvaluacionCriterio);

    void deleteById(Long id);

    List<PostulantesEvaluacionesCriterioDto> findByPostulanteEvaluacionId(Long postulanteEvaluacionId) throws Exception;

    int updateNotaCriterio(Long id, Long notaCriterio);
}