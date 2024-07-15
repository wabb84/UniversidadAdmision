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
import com.universidadadmision.produccion.dto.VacantesDto;
import com.universidadadmision.produccion.dto.VacantesDtoR;
import com.universidadadmision.produccion.entity.Grupo;
import com.universidadadmision.produccion.entity.Vacantes;
import com.universidadadmision.produccion.service.VacantesService;

@Controller
@CrossOrigin
@RequestMapping ("/vacantes")
@Validated
public class VacantesController {
	@Autowired
	private VacantesService vacantesservice;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoVacantes(@RequestBody VacantesDtoR vacantesDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();
		
		Vacantes vacantesnew = new Vacantes();
		vacantesnew.setPeriodo_id(vacantesDtor.getPeriodo_id());
		vacantesnew.setSede_id(vacantesDtor.getSede_id());
		vacantesnew.setCarrera_id(vacantesDtor.getCarrera_id());
		vacantesnew.setVacantes(vacantesDtor.getVacantes());
		vacantesnew.setEstado(true);
		vacantesnew.prePersist();
		
		try {
			vacantesservice.save(vacantesnew);
		
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Vacantes : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Vacantes grabados correctamente");
		response.put("dato",vacantesnew);
		
		return ResponseEntity.ok(response);			
	}
	
	@PostMapping("/edita")
	public ResponseEntity<?> EditaVacantes(@RequestBody VacantesDtoR vacantesDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Vacantes vacantesedita = vacantesservice.read(vacantesDtor.getId());

		if (vacantesedita == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe la Vacante");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		vacantesedita.setPeriodo_id(vacantesDtor.getPeriodo_id());
		vacantesedita.setSede_id(vacantesDtor.getSede_id());
		vacantesedita.setCarrera_id(vacantesDtor.getCarrera_id());
		vacantesedita.setVacantes(vacantesDtor.getVacantes());
		vacantesedita.setEstado(vacantesDtor.isEstado());
		vacantesedita.preUpdate();
		try {
			
			vacantesservice.save(vacantesedita);
	
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Vacantes : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Vacantes grabados correctamente");
		response.put("dato",vacantesedita);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaVacantes(@RequestBody VacantesDtoR vacantesDtor, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<>();
		
		Vacantes vacanteselimina = vacantesservice.read(vacantesDtor.getId());
		
		if (vacanteselimina == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe la Vacante");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		try {
			vacantesservice.delete(vacantesDtor.getId());
		} catch (Exception e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Eliminar la Vacante : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		}    
		
		response.put("resultado", 1);
		response.put("mensaje", "Vacante eliminada correctamente");
		response.put("dato",vacanteselimina);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/lista")
	public ResponseEntity<?> ListaVacantes() throws Exception {
		List<VacantesDto> vacanteslista = vacantesservice.listartodos();
		return ResponseEntity.ok(vacanteslista);
	}
	
	
}
