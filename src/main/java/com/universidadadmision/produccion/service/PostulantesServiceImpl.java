package com.universidadadmision.produccion.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.dto.PostulantesadjuntoDtoR;
import com.universidadadmision.produccion.entity.Periodo;
import com.universidadadmision.produccion.entity.Persona;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.entity.Vacantes;
import com.universidadadmision.produccion.repository.PostulantesRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostulantesServiceImpl implements PostulantesService {
	@Autowired
	private PostulantesRepository postulantesrep;
	
	@Autowired
	private VacantesService vacantesservice;
	
	@Autowired
	private PersonaService personaservice;
	
	@Autowired
	private PeriodoService periodoservice;
	
	@Transactional
	@Override
	public Postulantes save(Postulantes postulantes) {
		return postulantesrep.save( postulantes );
	}
	
	@Transactional
	@Override
	public void registropostulantesrequisitos(PostulantesadjuntoDtoR postulanteadjuntoDtor, List<MultipartFile> archivos) throws IOException  {
		Vacantes vacante = vacantesservice.findByPeriodoidAndSedeidAndCarreraid(postulanteadjuntoDtor.getPeriodoid(),postulanteadjuntoDtor.getSedeid(), postulanteadjuntoDtor.getCarreraid());
		if (vacante == null){
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No Existe Vacantes Segun el Periodo, Sede y Carrera Seleccionado");
		}
		
		Persona persona = personaservice.findByDocumento(postulanteadjuntoDtor.getTipodocumentoid(), postulanteadjuntoDtor.getNumerodocumento());
		Long idpersona = 0L; 
		if (persona == null){
			
			Persona personanew = new Persona();
			personanew.setNombre(postulanteadjuntoDtor.getNombre());
			personanew.setApellido_paterno(postulanteadjuntoDtor.getApellido_paterno());
			personanew.setApellido_materno(postulanteadjuntoDtor.getApellido_materno());
			personanew.setTipodocumentoid(postulanteadjuntoDtor.getTipodocumentoid());
			personanew.setNrodocumento(postulanteadjuntoDtor.getNumerodocumento());
			personanew.setSexo(postulanteadjuntoDtor.getSexo());
			personanew.setEmail(postulanteadjuntoDtor.getEmail());
			personanew.setCelular(postulanteadjuntoDtor.getCelular());
			personanew.setTelefono(postulanteadjuntoDtor.getTelefono());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate fechanacimiento = LocalDate.parse(postulanteadjuntoDtor.getFecha_nacimiento(), formatter);
			personanew.setFecha_nacimiento(fechanacimiento);
			personanew.setDireccion(postulanteadjuntoDtor.getDireccion());
			personanew.setUbigeo_id(postulanteadjuntoDtor.getUbigeo_id());
			personanew.setEstado(true);
			
			//personanew.prePersist();
			
			try {
				personaservice.save(personanew);
				idpersona = personanew.getId();
			} catch (Exception  e) {
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al Grabar Datos del Postulante : " + e.getMessage());
			} 
		}
		else {
			idpersona = persona.getId();
			//List<Postulantes> postulantebus = postulanteservice.findpostulantevacante(idpersona, vacante.getId());
			List<Postulantes> postulantebus = findpostulantevacante(idpersona, vacante.getId());
			if (postulantebus != null){
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Postulante ya registrado");
			}
		}
		
		
		Postulantes postulantenew = new Postulantes();
		postulantenew.setPersonaid(idpersona);
		postulantenew.setVacanteid(vacante.getId());
		GeneralDto codpostulantedto = generacodigo(postulanteadjuntoDtor.getPeriodoid());
		Periodo periodo = periodoservice.findByid(postulanteadjuntoDtor.getPeriodoid());
		String codpostulante = periodo.getAnio_semestre() + codpostulantedto.getCodigo();  
		postulantenew.setCodigo(codpostulante);		
		postulantenew.setGrupo_id(postulanteadjuntoDtor.getGrupoid());
		postulantenew.setModalidad_ingreso_id(postulanteadjuntoDtor.getModalidadid());
		postulantenew.setEstado_postulante("R");
		postulantenew.setEstado(true);
		//postulantenew.prePersist();
		
		
		
	}
	
	
	

	@Override
	public Postulantes read(Long id) {
		return postulantesrep.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		postulantesrep.deleteById(id);
	}

	@Override
	public List<PostulantesDto> listartodos() {
		return postulantesrep.ListaGrupo();
	}
	
	@Override
	public List<Postulantes> findpostulantevacante(Long idpersona, Long idvacante){
		return postulantesrep.findByPersonaidAndVacanteid(idpersona,idvacante);
	};
	
	@Transactional
	@Override
	public GeneralDto generacodigo(Long periodoid) {
		return postulantesrep.GeneraCodigoPostulante(periodoid);
	}
	
	@Override
	public List<PostulanteNotasDto> postulantenotaso(Long periodoid){
		return postulantesrep.PostulanteNotasO(periodoid);
	};
	
	@Override
	@Transactional
	public void postulantenotasi(List<PostulanteNotasIDtoR> postulantes){
		for (PostulanteNotasIDtoR postulante : postulantes) {
			Postulantes postulanteexis = postulantesrep.findById(postulante.getId()).orElse(null);
			
			postulanteexis.setNota(postulante.getNota());
			postulanteexis.setEstado_postulante("I");
			postulantesrep.save(postulanteexis);
			//System.out.println(postulante.getCodigo());
		}
		
	};
}
