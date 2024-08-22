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
import com.universidadadmision.produccion.dto.ReqModDtoR;
import com.universidadadmision.produccion.entity.RequisitosModalidad;
import com.universidadadmision.produccion.service.RequisitoModalidadService;

@Controller
@CrossOrigin
@RequestMapping ("/requisitomodalidad")
@Validated
public class RequisitoModalidadController {
	@Autowired
	private RequisitoModalidadService reqmodservice;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoReqMod(@RequestBody ReqModDtoR reqmodDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();
		
		RequisitosModalidad reqmodnew = new RequisitosModalidad();
		reqmodnew.setModalidadid(reqmodDtor.getModalidadid());

		reqmodnew.setRequisitoid(reqmodDtor.getRequisitoid());
		reqmodnew.setTipo_requisito(reqmodDtor.getTipo_requisito());
		reqmodnew.setEstado(true);
		reqmodnew.setRequisitoconadis(reqmodDtor.isRequisitoconadis());
		reqmodnew.prePersist();
		
		try {
			reqmodservice.save(reqmodnew);
		
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar el Requerimiento por Modalidad : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Requerimiento por Modalidad grabados correctamente");
		response.put("dato",reqmodnew);
		
		return ResponseEntity.ok(response);			
	}
	
	@PostMapping("/edita")
	public ResponseEntity<?> EditaReqMod(@RequestBody ReqModDtoR reqmodDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		RequisitosModalidad reqmodedit = reqmodservice.read(reqmodDtor.getId());

		if (reqmodedit == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Requisito por Modalidad");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		reqmodedit.setRequisitoid(reqmodDtor.getRequisitoid());
		reqmodedit.setTipo_requisito(reqmodDtor.getTipo_requisito());
		reqmodedit.setEstado(reqmodDtor.isEstado());
		reqmodedit.setRequisitoconadis(reqmodDtor.isRequisitoconadis());
		reqmodedit.preUpdate();

		try {
			
			reqmodservice.save(reqmodedit);
	
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Requisito por Modalidad : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Requisito por Modalidad grabados correctamente");
		response.put("dato",reqmodedit);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaReqMod(@RequestBody ReqModDtoR reqmodDtor, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<>();

		RequisitosModalidad reqmodelimina = reqmodservice.read(reqmodDtor.getId());
		
		if (reqmodelimina == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Requisito por Modalidad");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		try {
			reqmodservice.delete(reqmodDtor.getId());
		} catch (Exception e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Eliminar el Requisito por Modalidad : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		}    
		
		response.put("resultado", 1);
		response.put("mensaje", "Requisito por Modalidad eliminado correctamente");
		response.put("dato",reqmodelimina);
		return ResponseEntity.ok(response);
	}
}