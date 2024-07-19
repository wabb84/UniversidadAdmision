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
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.GrupoDtoR;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.entity.Periodo;
import com.universidadadmision.produccion.entity.Persona;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.entity.Vacantes;
import com.universidadadmision.produccion.service.PeriodoService;
import com.universidadadmision.produccion.service.PersonaService;
import com.universidadadmision.produccion.service.PostulantesService;
import com.universidadadmision.produccion.service.VacantesService;

@Controller
@CrossOrigin
@RequestMapping ("/postulantes")
@Validated
public class PostulanteController {
	@Autowired
	private PostulantesService postulanteservice;

	@Autowired
	private PersonaService personaservice;
	
	@Autowired
	private VacantesService vacantesservice;
	
	@Autowired
	private PeriodoService periodoservice;

	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoGrupo(@RequestBody PostulantesDtoR postulanteDtor) throws Exception  {
		Map<String, Object> response = new HashMap<>();

		Vacantes vacante = vacantesservice.findByPeriodoidAndSedeidAndCarreraid(postulanteDtor.getPeriodoid(), postulanteDtor.getSedeid(), postulanteDtor.getCarreraid());
		if (vacante == null){
			response.put("resultado", 0);
			response.put("mensaje", "No Existe Vacantes Segun el Periodo, Sede y Carrera Seleccionado");
			response.put("dato","");
			
			return ResponseEntity.ok(response);
		}
		
		Persona persona = personaservice.findByDocumento(postulanteDtor.getTipodocumentoid(), postulanteDtor.getNumerodocumento());
		Long idpersona = 0L; 
		if (persona == null){
			Persona personanew = new Persona();
			personanew.setNombre(postulanteDtor.getNombre());
			personanew.setApellido_paterno(postulanteDtor.getApellido_paterno());
			personanew.setApellido_materno(postulanteDtor.getApellido_materno());
			personanew.setTipodocumentoid(postulanteDtor.getTipodocumentoid());
			personanew.setNrodocumento(postulanteDtor.getNumerodocumento());
			personanew.setSexo(postulanteDtor.getSexo());
			personanew.setEmail(postulanteDtor.getEmail());
			personanew.setCelular(postulanteDtor.getCelular());
			personanew.setTelefono(postulanteDtor.getTelefono());
			personanew.setFecha_nacimiento(postulanteDtor.getFecha_nacimiento());
			personanew.setDireccion(postulanteDtor.getDireccion());
			personanew.setUbigeo_id(postulanteDtor.getUbigeo_id());
			personanew.setEstado(true);
			personanew.prePersist();
			
			try {
				personaservice.save(personanew);
				idpersona = personanew.getId();
			} catch (Exception  e) {
				  response.put("resultado", 0);
				  response.put("mensaje", "Error al Grabar Datos del Postulante : " + e.getMessage());
				  response.put("dato","");
			      return ResponseEntity.ok(response);
			} 
		}
		else {
			idpersona = persona.getId();
			List<Postulantes> postulantebus = postulanteservice.findpostulantevacante(idpersona, vacante.getId());
			if (postulantebus != null){
				response.put("resultado", 0);
				response.put("mensaje", "Postulante ya Registrado");
				response.put("dato","");
			    return ResponseEntity.ok(response);
			}
		}
				
		Postulantes postulantenew = new Postulantes();
		postulantenew.setPersonaid(idpersona);
		postulantenew.setVacanteid(vacante.getId());
		GeneralDto codpostulantedto = postulanteservice.generacodigo(postulanteDtor.getPeriodoid());
		Periodo periodo = periodoservice.findByid(postulanteDtor.getPeriodoid());
		String codpostulante = periodo.getAnio_semestre() + codpostulantedto.getCodigo();  
		postulantenew.setCodigo(codpostulante);		
		postulantenew.setGrupo_id(postulanteDtor.getGrupoid());
		postulantenew.setModalidad_ingreso_id(postulanteDtor.getModalidadid());
		postulantenew.setEstado_postulante("R");
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
		response.put("dato",postulantenew);
		
		return ResponseEntity.ok(response);			
	}
	
	@PostMapping("/lista")
	public ResponseEntity<?> ListaPostulante() throws Exception {
		List<PostulantesDto> postulantelista = postulanteservice.listartodos();
		return ResponseEntity.ok(postulantelista);
	}
	
	@PostMapping("/postulantenotaso")
	public ResponseEntity<?> ListaPostulanteNotasO(@RequestBody GrupoDtoR grupodtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		
		Periodo periodo = periodoservice.findByid(grupodtor.getPeriodo_id());
		if (periodo == null){
			response.put("resultado", 0);
			response.put("mensaje", "Periodo Seleccionado no Existe");
			response.put("dato","");
			return ResponseEntity.ok(response);
		}
		
		List<PostulanteNotasDto> postulantesnotaso = postulanteservice.postulantenotaso(grupodtor.getPeriodo_id());
		return ResponseEntity.ok(postulantesnotaso);
	}
	
	@PostMapping("/postulantenotasi")
	public ResponseEntity<?> ListaPostulanteNotasI(@RequestBody List<PostulanteNotasIDtoR> postulantesi) throws Exception {
		Map<String, Object> response = new HashMap<>();
		postulanteservice.postulantenotasi(postulantesi);
		//Hacer Validaciones
		//Cual es el criterio segun la Nota para que el Alumno pase de ser Postulante a ser Ingresante
		
		
		//Periodo periodo = periodoservice.findByid(grupodtor.getPeriodo_id());
		//if (periodo == null){
		response.put("resultado", 0);
		response.put("mensaje", "Proceso de Notas generado correctamente");
		response.put("dato","");
		return ResponseEntity.ok(response);
		
		//}
		
		//List<PostulanteNotasDto> postulantesnotaso = postulanteservice.postulantenotaso(grupodtor.getPeriodo_id());
		//return ResponseEntity.ok(postulantesnotaso);
	}

	
	/*@PostMapping("/edita")
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
		
		postulanteedita.setPersonaid(persona.getId());
		//postulanteedita.setVacanteid(postulanteDtor.getVacanteid());
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
	}*/
	
	@PostMapping("/elimina")
    public ResponseEntity<?> EliminaPostulante(@RequestBody PostulantesDtoR postulanteDtor, BindingResult result) throws Exception{
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
