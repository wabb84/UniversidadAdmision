package com.universidadadmision.produccion.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Postulantes_Evaluacion_Criterio", schema = "Admision")
public class PostulantesEvaluacionCriterio extends Auditable<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "postulante_evaluacion_id", nullable = false)
    private Long postulanteEvaluacionId;

    @Column(name = "criterio_evaluacion_id", nullable = false)
    private Long criterioEvaluacionId;

    @Column(name = "evaluacion_id", nullable = false)
    private Long evaluacionId;

    @Column(name = "nota_criterio", nullable = true, precision = 5, scale = 2)
    private Long notaCriterio;

}
