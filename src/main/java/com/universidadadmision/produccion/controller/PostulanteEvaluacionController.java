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

import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.PostulanteEvaluacionDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
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

    @PostMapping("/lista")
    public ResponseEntity<?> listaPostulantes(@RequestBody PostulantesDtoR postulanteDto) {
        try {
            List<PostulantesEvaluacionDto> postulantes = postulantesEvaluacionService
                    .listaPostulantesEvaluacion(postulanteDto.getId());
            return ResponseEntity.ok(postulantes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar los postulantes de evaluación: " + e.getMessage());
        }
    }

    @PostMapping("/buscar")
    public ResponseEntity<?> buscarPorPostulanteId(@RequestBody PostulantesDtoR postulanteDto) throws Exception {
        try {
            List<PostulantesEvaluacionesDto> postulantesEvaluacionList = postulantesEvaluacionService
                    .findByPostulanteId(postulanteDto.getId());

            return ResponseEntity.ok(postulantesEvaluacionList);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al listar las evaluaciones del postulante: " + e.getMessage());
        }
    }

    @PostMapping("/buscar-detalle")
    public ResponseEntity<?> buscarPorPostulanteEvaluacionId(
        @RequestBody PostulantesDtoR postulanteDto)
            throws Exception {
        try {
            List<PostulantesEvaluacionesCriterioDto> postulantesEvaluacionList = postulantesEvaluacionCriterioService
                    .findByPostulanteEvaluacionId(postulanteDto.getId());
            return ResponseEntity.ok(postulantesEvaluacionList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar los criterios de evaluación: " + e.getMessage());
        }
    }

    @PostMapping("/actualizar-detalle")
    public ResponseEntity<?> updateNotaCriterio(
        @RequestBody PostulanteEvaluacionDtoR postulanteDto) {
       Map<String, Object> response = new HashMap<>();
		MigraAcadDto migraacad = postulantesEvaluacionCriterioService.executeActualizarNota(postulanteDto.getId(), postulanteDto.getNota());
		if (migraacad.getCodigo() == 0) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al actualizar nota de postulante : " + migraacad.getDescripcion());
			response.put("dato", migraacad);
		} else {
			response.put("resultado", 1);
			response.put("mensaje", "Actualización de nota exitosa.");
			response.put("dato", migraacad);
		}
		return ResponseEntity.ok(response);
    }
}
