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
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.entity.Persona;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.service.PersonaService;
import com.universidadadmision.produccion.service.PostulantesService;

@Controller
@CrossOrigin
@RequestMapping ("/postulantes")
@Validated
public class PostulanteController {
	@Autowired
	private PostulantesService postulanteservice;

	@Autowired
	private PersonaService personaservice;

	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoGrupo(@RequestBody PostulantesDtoR postulanteDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();
		
		Persona persona = personaservice.findByDocumento(postulanteDtor.getTipodocumentoid(), postulanteDtor.getNumerodocumento());
		if (persona == null){
			response.put("resultado", 0);
			response.put("mensaje", "Tipo y Número de Documento ingresado No existe");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		Postulantes postulantenew = new Postulantes();
		postulantenew.setPersona_id(persona.getId());
		postulantenew.setVacante_id(postulanteDtor.getVacanteid());
		postulantenew.setGrupo_id(postulanteDtor.getGrupoid());
		postulantenew.setModalidad_ingreso_id(postulanteDtor.getModalidadid());
		postulantenew.setEstado_postulante(postulanteDtor.getEstadopostulante());
		postulantenew.setCodigo("0000000001");
		postulantenew.setEstado(true);
		postulantenew.prePersist();

		try {
			postulanteservice.save(postulantenew);
		
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar el Postulante : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos del Postulante grabados correctamente");
		response.put("dato","");
		
		return ResponseEntity.ok(postulantenew);			
	}
	
	@PostMapping("/lista")
	public ResponseEntity<?> ListaPostulante() throws Exception {
		List<PostulantesDto> postulantelista = postulanteservice.listartodos();
		return ResponseEntity.ok(postulantelista);
	}
	
	@PostMapping("/edita")
	public ResponseEntity<?> EditaPostulante(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		
		Persona persona = personaservice.findByDocumento(postulanteDtor.getTipodocumentoid(), postulanteDtor.getNumerodocumento());
		if (persona == null){
			response.put("resultado", 0);
			response.put("mensaje", "Tipo y Número de Documento ingresado No existe");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		Postulantes postulanteedita = postulanteservice.read(postulanteDtor.getId());

		if (postulanteedita == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Postulante");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		postulanteedita.setPersona_id(persona.getId());
		postulanteedita.setVacante_id(postulanteDtor.getVacanteid());
		postulanteedita.setGrupo_id(postulanteDtor.getGrupoid());
		postulanteedita.setModalidad_ingreso_id(postulanteDtor.getModalidadid());
		postulanteedita.setEstado_postulante(postulanteDtor.getEstadopostulante());
		postulanteedita.setCodigo("0000000001");
		postulanteedita.setEstado(postulanteDtor.isEstado());
		postulanteedita.preUpdate();
		
		try {
			
			postulanteservice.save(postulanteedita);
	
		} catch (Exception  e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Grabar Postulante : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		} 
		
		response.put("resultado", 1);
		response.put("mensaje", "Datos de Postulante grabados correctamente");
		response.put("dato",postulanteedita);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaGrupo(@RequestBody PostulantesDtoR postulanteDtor, BindingResult result) throws Exception{
		Map<String, Object> response = new HashMap<>();
		
		Postulantes postulanteselimina = postulanteservice.read(postulanteDtor.getId());
		
		if (postulanteselimina == null){
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Postulante");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		try {
			postulanteservice.delete(postulanteDtor.getId());
		} catch (Exception e) {
			  response.put("resultado", 0);
			  response.put("mensaje", "Error al Eliminar el Postulante : " + e.getMessage());
			  response.put("dato","");
		      return ResponseEntity.ok(response);
		}    
		
		response.put("resultado", 1);
		response.put("mensaje", "Postulante eliminado correctamente");
		response.put("dato",postulanteselimina);
		return ResponseEntity.ok(response);
	}
	
}
