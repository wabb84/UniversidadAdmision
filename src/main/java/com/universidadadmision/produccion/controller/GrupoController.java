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
import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.dto.GrupoDtoR;
import com.universidadadmision.produccion.entity.Grupo;
import com.universidadadmision.produccion.service.GrupoService;

@Controller
@CrossOrigin
@RequestMapping ("/grupo")
@Validated
public class GrupoController {
	@Autowired
	private GrupoService gruposervice;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoGrupo(@RequestBody GrupoDtoR grupoDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();
		
		Grupo gruponew = new Grupo();
		gruponew.setNombre(grupoDtor.getNombre());
		gruponew.setPeriodo_id(grupoDtor.getPeriodo_id());
		gruponew.setTipo_ingreso_id(grupoDtor.getTipo_ingreso_id());
		gruponew.setFecha_inicio(grupoDtor.getFecha_inicio());
		gruponew.setFecha_fin(grupoDtor.getFecha_fin());
		gruponew.setFecha_inicio_evaluacion(grupoDtor.getFecha_inicio_evaluacion());
		gruponew.setFecha_fin_evaluacion(grupoDtor.getFecha_fin_evaluacion());
		gruponew.setEstado(true);
		gruponew.prePersist();
		try {
			gruposervice.save(gruponew);
		
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar el Grupo : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Grupo grabados correctamente");
		response.put("dato",gruponew);
		
		return ResponseEntity.ok(response);			
	}
	
	@PostMapping("/edita")
	public ResponseEntity<?> EditaGrupo(@RequestBody GrupoDtoR grupoDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Grupo grupodedita = gruposervice.read(grupoDtor.getId());

		if (grupodedita == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Grupo");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		grupodedita.setNombre(grupoDtor.getNombre());
		grupodedita.setPeriodo_id(grupoDtor.getPeriodo_id());
		grupodedita.setTipo_ingreso_id(grupoDtor.getTipo_ingreso_id());
		grupodedita.setFecha_inicio(grupoDtor.getFecha_inicio());
		grupodedita.setFecha_fin(grupoDtor.getFecha_fin());
		grupodedita.setFecha_inicio_evaluacion(grupoDtor.getFecha_inicio_evaluacion());
		grupodedita.setFecha_fin_evaluacion(grupoDtor.getFecha_fin_evaluacion());
		grupodedita.setEstado(grupoDtor.isEstado());
		grupodedita.preUpdate();
		try {
			
			gruposervice.save(grupodedita);
	
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Grupo : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Grupo grabados correctamente");
		response.put("dato",grupodedita);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/lista")
	public ResponseEntity<?> ListaGrupo() throws Exception {
		List<GrupoDto> grupolista = gruposervice.listartodos();
		return ResponseEntity.ok(grupolista);
	}
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaGrupo(@RequestBody GrupoDtoR grupoDtor, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<>();
		
		Grupo grupoelimina = gruposervice.read(grupoDtor.getId());
		
		if (grupoelimina == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Grupo");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		try {
			gruposervice.delete(grupoDtor.getId());
		} catch (Exception e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Eliminar el Grupo : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		}    
		
		response.put("resultado", 1);
		response.put("mensaje", "Grupo eliminado correctamente");
		response.put("dato",grupoelimina);
		return ResponseEntity.ok(response);
	}
}
