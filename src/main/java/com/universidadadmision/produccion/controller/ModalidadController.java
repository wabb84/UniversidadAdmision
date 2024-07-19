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

import com.universidadadmision.produccion.dto.GrupoDtoRxperiodo;
import com.universidadadmision.produccion.dto.ModalidadDto;
import com.universidadadmision.produccion.dto.ModalidadDtoR;
import com.universidadadmision.produccion.entity.Modalidad;
import com.universidadadmision.produccion.service.ModalidadService;


@Controller
@CrossOrigin
@RequestMapping ("/modalidad")
@Validated
public class ModalidadController {
	@Autowired
	private ModalidadService modalidadservice;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoModalidad(@RequestBody ModalidadDtoR modalidadDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();
		
		Modalidad modalidadnew = new Modalidad();
		modalidadnew.setNombre(modalidadDtor.getNombre());
		modalidadnew.setTipoingresoid(modalidadDtor.getTipo_ingreso_id());
		modalidadnew.setEstado(true);
		modalidadnew.prePersist();
		try {
			modalidadservice.save(modalidadnew);
		
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar la Modalidad : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Modalidad grabados correctamente");
		response.put("dato",modalidadnew);
		
		return ResponseEntity.ok(response);			
	}
	
	
	@PostMapping("/lista")
	public ResponseEntity<?> ListaModalidad() throws Exception {
		List<ModalidadDto> modalidadlista = modalidadservice.listartodos();
		return ResponseEntity.ok(modalidadlista);
	}
	
	@PostMapping("/listaxtipoingreso")
	public ResponseEntity<?> ListaModalidadxTipoIngreso(@RequestBody GrupoDtoRxperiodo grupoxperiodo) throws Exception {
		List<Modalidad> modalidadlistaxtipoingreso = modalidadservice.listarxtipoingreso(grupoxperiodo.getIdtipo());
		return ResponseEntity.ok(modalidadlistaxtipoingreso);
	}
	
	@PostMapping("/edita")
	public ResponseEntity<?> EditaModalidad(@RequestBody ModalidadDtoR modalidadDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Modalidad modalidadedita = modalidadservice.read(modalidadDtor.getId());

		if (modalidadedita == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe la Modalidad");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		modalidadedita.setNombre(modalidadDtor.getNombre());
		modalidadedita.setTipoingresoid(modalidadDtor.getTipo_ingreso_id());
		modalidadedita.setEstado(modalidadDtor.isEstado());
		modalidadedita.preUpdate();
		try {
			
			modalidadservice.save(modalidadedita);
	
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Tipo de Ingreso : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Tipo Ingreso grabados correctamente");
		response.put("dato",modalidadedita);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaModalidad(@RequestBody ModalidadDtoR modalidadDtor, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<>();
		
		Modalidad modalidadelimina = modalidadservice.read(modalidadDtor.getId());
		
		if (modalidadelimina == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe la Molidad");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		try {
			modalidadservice.delete(modalidadDtor.getId());
		} catch (Exception e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Eliminar Modalidad : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		}    
		
		response.put("resultado", 1);
		response.put("mensaje", "Modalidad eliminada correctamente");
		response.put("dato",modalidadelimina);
		return ResponseEntity.ok(response);
	}
}
