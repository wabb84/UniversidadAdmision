package com.universidadadmision.produccion.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidadadmision.produccion.dto.PostulantesEvaluacionDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesCriterioDto;
import com.universidadadmision.produccion.dto.PostulantesEvaluacionesDto;
import com.universidadadmision.produccion.service.PostulantesEvaluacionCriterioService;
import com.universidadadmision.produccion.service.PostulantesEvaluacionService;

@RestController
@CrossOrigin
@RequestMapping("/postulante-evaluacion")
public class PostulanteEvaluacionController {
    @Autowired
    private PostulantesEvaluacionService postulantesEvaluacionService;

    @Autowired
    private PostulantesEvaluacionCriterioService postulantesEvaluacionCriterioService;

    @GetMapping("/lista")
    public ResponseEntity<?> listaPostulantes(@RequestParam("grupoId") Long grupoId) {
        try {
            List<PostulantesEvaluacionDto> postulantes = postulantesEvaluacionService
                    .listaPostulantesEvaluacion(grupoId);
            return ResponseEntity.ok(postulantes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar los postulantes de evaluación: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorPostulanteId(@RequestParam("postulanteId") Long postulanteId) throws Exception {
        try {
            List<PostulantesEvaluacionesDto> postulantesEvaluacionList = postulantesEvaluacionService
                    .findByPostulanteId(postulanteId);

            return ResponseEntity.ok(postulantesEvaluacionList);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al listar las evaluaciones del postulante: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-detalle")
    public ResponseEntity<?> buscarPorPostulanteEvaluacionId(
            @RequestParam("postulanteEvaluacionId") Long postulanteEvaluacionId)
            throws Exception {
        try {
            List<PostulantesEvaluacionesCriterioDto> postulantesEvaluacionList = postulantesEvaluacionCriterioService
                    .findByPostulanteEvaluacionId(postulanteEvaluacionId);
            return ResponseEntity.ok(postulantesEvaluacionList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar los criterios de evaluación: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar-detalle/{id}/{monto}")
    public ResponseEntity<?> updateNotaCriterio(
        @PathVariable Long id,
        @PathVariable Long monto) {

        Map<String, Object> response = new HashMap<>();

        int updatedRows = postulantesEvaluacionCriterioService.updateNotaCriterio(id, monto);

        if (updatedRows > 0) {
            response.put("resultado", 1);
            response.put("mensaje", "Actualización de nota exitosa.");
            response.put("dato", "");
            return ResponseEntity.ok(response);
        } else {
            response.put("resultado", 0);
            response.put("mensaje", "Error en la actualización de nota.");
            response.put("dato", "");
            return ResponseEntity.ok(response);
        }
    }
}
