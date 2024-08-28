package com.universidadadmision.produccion.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionesCriterioDto;
import com.universidadadmision.produccion.entity.PostulantesEvaluacionCriterio;
import com.universidadadmision.produccion.repository.PostulantesEvaluacionCriterioRepository;

import jakarta.transaction.Transactional;

@Service
public class PostulantesEvaluacionCriterioServiceImpl implements PostulantesEvaluacionCriterioService {

    @Autowired
    private PostulantesEvaluacionCriterioRepository postulanteEvaluacionCriterioRepository;

    @Override
    public List<PostulantesEvaluacionCriterio> findAll() {
        return postulanteEvaluacionCriterioRepository.findAll();
    }

    @Override
    public Optional<PostulantesEvaluacionCriterio> findById(Long id) {
        return postulanteEvaluacionCriterioRepository.findById(id);
    }

    @Override
    public PostulantesEvaluacionCriterio save(PostulantesEvaluacionCriterio postulanteEvaluacionCriterio) {
        return postulanteEvaluacionCriterioRepository.save(postulanteEvaluacionCriterio);
    }

    @Override
    public void deleteById(Long id) {
        postulanteEvaluacionCriterioRepository.deleteById(id);
    }

    @Override
    public List<PostulantesEvaluacionesCriterioDto> findByPostulanteEvaluacionId(Long postulanteEvaluacionId)
            throws Exception {
        return postulanteEvaluacionCriterioRepository.findByPostulanteEvaluacionId(postulanteEvaluacionId);

    }

    @Override
    @Transactional
    public int updateNotaCriterio(Long id, Long notaCriterio) {
        return postulanteEvaluacionCriterioRepository.updateNotaCriterioById(id, notaCriterio);
    }
}