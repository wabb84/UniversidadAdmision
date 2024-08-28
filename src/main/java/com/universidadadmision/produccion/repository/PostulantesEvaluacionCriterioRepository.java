package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionesCriterioDto;
import com.universidadadmision.produccion.entity.PostulantesEvaluacionCriterio;

@Repository
public interface PostulantesEvaluacionCriterioRepository extends JpaRepository<PostulantesEvaluacionCriterio, Long> {
    @Transactional(readOnly = true)
    @Query(value = "SELECT a.id,a.criterio_evaluacion_id,b.nombre as nombre_criterio_evaluacion,nota_criterio, b.puntaje\r\n"
            +
            "FROM Admision.Postulantes_Evaluacion_Criterio A\r\n" +
            "INNER JOIN Admision.Criterio_Evaluacion b on a.criterio_evaluacion_id = b.id WHERE a.postulante_evaluacion_id = :postulanteEvaluacionId", nativeQuery = true)
    List<PostulantesEvaluacionesCriterioDto> findByPostulanteEvaluacionId(Long postulanteEvaluacionId) throws Exception;

    @Modifying
    @Transactional
    @Query(value = "UPDATE Admision.Postulantes_Evaluacion_Criterio SET nota_criterio = :notaCriterio WHERE id = :id", nativeQuery = true)
    int updateNotaCriterioById(Long id, Long notaCriterio);
}
