package com.universidadadmision.produccion.controller;

import java.util.HashMap;
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
import com.universidadadmision.produccion.dto.Tipo_IngresoDto;
import com.universidadadmision.produccion.dto.Tipo_IngresoDtoR;
import com.universidadadmision.produccion.entity.Tipo_Ingreso;
import com.universidadadmision.produccion.service.Tipo_IngresoService;
import java.util.List;


@Controller
@CrossOrigin
@RequestMapping ("/tipoingreso")
@Validated
public class Tipo_IngresoController {

	@Autowired
	private Tipo_IngresoService tipo_ingresoservice;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoTipoIngreso(@RequestBody Tipo_IngresoDtoR tipo_ingresoDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();
		Tipo_Ingreso tipo_ingresonew = new Tipo_Ingreso();
		tipo_ingresonew.setNombre(tipo_ingresoDtor.getNombre());
		tipo_ingresonew.setEstado(true);
		tipo_ingresonew.prePersist();
		try {
			tipo_ingresoservice.save(tipo_ingresonew);
		
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Tipo de Ingreso : " + e.getMessage());
			  response.put("dato","");
		      //return new ResponseEntity<Map<String,Object>>(response , HttpStatus.BAD_REQUEST);
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Tipo Ingreso grabados correctamente");
		response.put("dato",tipo_ingresonew);
		
		return ResponseEntity.ok(response);			
	}
	
	@PostMapping("/lista")
	public ResponseEntity<?> ListaTipoIngreso() throws Exception {
		List<Tipo_IngresoDto> tipoingresolista = tipo_ingresoservice.listartodos();
		return ResponseEntity.ok(tipoingresolista);
	}
	
	@PostMapping("/edita")
	public ResponseEntity<?> EditaTipoIngreso(@RequestBody Tipo_IngresoDtoR tipo_ingresoDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Tipo_Ingreso tipoingresoedita = tipo_ingresoservice.read(tipo_ingresoDtor.getId());

		if (tipoingresoedita == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Tipo de Ingreso ");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		tipoingresoedita.setNombre(tipo_ingresoDtor.getNombre());
		tipoingresoedita.setEstado(tipo_ingresoDtor.isEstado());
		tipoingresoedita.preUpdate();
		try {
			
			tipo_ingresoservice.save(tipoingresoedita);
	
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Tipo de Ingreso : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Tipo Ingreso grabados correctamente");
		response.put("dato",tipoingresoedita);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaTipo_Ingreso(@RequestBody Tipo_IngresoDtoR tipo_ingresoDtor, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<>();
		
		Tipo_Ingreso tipoingresoelimina = tipo_ingresoservice.read(tipo_ingresoDtor.getId());
		
		if (tipoingresoelimina == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Tipo de Ingreso ");
			response.put("dato","");
			return ResponseEntity.ok(response);
		}
		
		try {
			tipo_ingresoservice.delete(tipo_ingresoDtor.getId());
		} catch (Exception e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Eliminar Tipo de Ingreso : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		}    
		
		response.put("resultado", 1);
		response.put("mensaje", "Tipo Ingreso eliminado correctamente");
		response.put("dato",tipoingresoelimina);
		return ResponseEntity.ok(response);
	}
	
}
