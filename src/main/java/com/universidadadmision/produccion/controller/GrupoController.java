package com.universidadadmision.produccion.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.universidadadmision.produccion.dto.CargaNotasDtoR;
import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.dto.GrupoDtoR;
import com.universidadadmision.produccion.dto.GrupoDtoRxperiodo;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.entity.Grupo;
import com.universidadadmision.produccion.service.GrupoService;

@Controller
@CrossOrigin
@RequestMapping("/grupo")
@Validated
public class GrupoController {
	@Autowired
	private GrupoService gruposervice;

	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoGrupo(@RequestBody GrupoDtoR grupoDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Grupo gruponew = new Grupo();
		gruponew.setNombre(grupoDtor.getNombre());
		gruponew.setPeriodoid(grupoDtor.getPeriodo_id());
		gruponew.setTipoingresoid(grupoDtor.getTipo_ingreso_id());
		gruponew.setFecha_inicio(grupoDtor.getFecha_inicio());
		gruponew.setFecha_fin(grupoDtor.getFecha_fin());
		gruponew.setFecha_inicio_evaluacion(grupoDtor.getFecha_inicio_evaluacion());
		gruponew.setFecha_fin_evaluacion(grupoDtor.getFecha_fin_evaluacion());
		gruponew.setPuntajemaximo(grupoDtor.getPuntajemaximo());
		gruponew.setPuntajeaprobatorio(grupoDtor.getPuntajeaprobatorio());
		gruponew.setEstado(true);
		gruponew.prePersist();
		try {
			gruposervice.save(gruponew);

		} catch (Exception e) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al Grabar el Grupo : " + e.getMessage());
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		response.put("resultado", 1);
		response.put("mensaje", "Datos de Grupo grabados correctamente");
		response.put("dato", gruponew);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/edita")
	public ResponseEntity<?> EditaGrupo(@RequestBody GrupoDtoR grupoDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Grupo grupodedita = gruposervice.read(grupoDtor.getId());

		if (grupodedita == null) {
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Grupo");
			response.put("dato", "");

			return ResponseEntity.ok(response);
		}

		grupodedita.setNombre(grupoDtor.getNombre());
		grupodedita.setPeriodoid(grupoDtor.getPeriodo_id());
		grupodedita.setTipoingresoid(grupoDtor.getTipo_ingreso_id());
		grupodedita.setFecha_inicio(grupoDtor.getFecha_inicio());
		grupodedita.setFecha_fin(grupoDtor.getFecha_fin());
		grupodedita.setFecha_inicio_evaluacion(grupoDtor.getFecha_inicio_evaluacion());
		grupodedita.setFecha_fin_evaluacion(grupoDtor.getFecha_fin_evaluacion());
		grupodedita.setPuntajemaximo(grupoDtor.getPuntajemaximo());
		grupodedita.setPuntajeaprobatorio(grupoDtor.getPuntajeaprobatorio());
		grupodedita.setEstado(grupoDtor.isEstado());
		grupodedita.preUpdate();
		try {

			gruposervice.save(grupodedita);

		} catch (Exception e) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al Grabar Grupo : " + e.getMessage());
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		response.put("resultado", 1);
		response.put("mensaje", "Datos de Grupo grabados correctamente");
		response.put("dato", grupodedita);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/lista")
	public ResponseEntity<?> ListaGrupo() throws Exception {
		List<GrupoDto> grupolista = gruposervice.listartodos();
		return ResponseEntity.ok(grupolista);
	}

	@PostMapping("/listaxperido")
	public ResponseEntity<?> ListaGrupoxPeriodo(@RequestBody GrupoDtoRxperiodo grupoxperiodo) throws Exception {
		List<Grupo> grupolistaxperiodo = gruposervice.listarxperiodo(grupoxperiodo.getIdperiodo(),
				grupoxperiodo.getIdtipo());
		return ResponseEntity.ok(grupolistaxperiodo);
	}

	@PostMapping("/elimina")
	public ResponseEntity<?> EliminaGrupo(@RequestBody GrupoDtoR grupoDtor, BindingResult result) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Grupo grupoelimina = gruposervice.read(grupoDtor.getId());

		if (grupoelimina == null) {
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Grupo");
			response.put("dato", "");

			return ResponseEntity.ok(response);
		}

		try {
			gruposervice.delete(grupoDtor.getId());
		} catch (Exception e) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al Eliminar el Grupo : " + e.getMessage());
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		response.put("resultado", 1);
		response.put("mensaje", "Grupo eliminado correctamente");
		response.put("dato", grupoelimina);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/cargar-vacantes")
	public ResponseEntity<?> CargaNotasGrupo(@RequestBody CargaNotasDtoR carganotas) throws Exception {
		Map<String, Object> response = new HashMap<>();
		MigraAcadDto migraacad = gruposervice.executeCargarNotas(carganotas.getId());
		if (migraacad.getCodigo() == 0) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al realizar el Traslado de Notas : " + migraacad.getDescripcion());
			response.put("dato", migraacad);
		} else {
			response.put("resultado", 1);
			response.put("mensaje", "Traslado de notas ejecutado con éxito");
			response.put("dato", migraacad);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/cargar-notas-evaluaciones")
	public ResponseEntity<?> CargaNotasEvaluaciones(@RequestBody CargaNotasDtoR carganotas) throws Exception {
		Map<String, Object> response = new HashMap<>();
		MigraAcadDto migraacad = gruposervice.executeCargarNotasEvaluaciones(carganotas.getId());
		if (migraacad.getCodigo() == 0) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al realizar la carga de notas : " + migraacad.getDescripcion());
			response.put("dato", migraacad);
		} else {
			response.put("resultado", 1);
			response.put("mensaje", "Carga de notas ejecutado con éxito");
			response.put("dato", migraacad);
		}
		return ResponseEntity.ok(response);
	}
}
