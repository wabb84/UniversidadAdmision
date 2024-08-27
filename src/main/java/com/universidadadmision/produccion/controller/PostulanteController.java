package com.universidadadmision.produccion.controller;

//import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.universidadadmision.produccion.components.EmailService;
import com.universidadadmision.produccion.dto.GeneralDto;
import com.universidadadmision.produccion.dto.GrupoDtoR;
import com.universidadadmision.produccion.dto.MigraAcadDto;
import com.universidadadmision.produccion.dto.PostulanteGrupoDto;
import com.universidadadmision.produccion.dto.PostulanteNotasDto;
import com.universidadadmision.produccion.dto.PostulanteNotasIDtoR;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDtoR;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.dto.PostulantesDtoR;
import com.universidadadmision.produccion.dto.PostulantesadjuntoDtoR;
import com.universidadadmision.produccion.dto.ValidaPostulanteDto;
import com.universidadadmision.produccion.dto.ValidaPostulanteDtoR;
import com.universidadadmision.produccion.entity.Periodo;
import com.universidadadmision.produccion.entity.Persona;
import com.universidadadmision.produccion.entity.Postulantes;
import com.universidadadmision.produccion.entity.PostulantesRequisitos;
import com.universidadadmision.produccion.entity.Requisitos;
import com.universidadadmision.produccion.entity.RequisitosModalidad;
import com.universidadadmision.produccion.entity.Vacantes;
import com.universidadadmision.produccion.service.PeriodoService;
import com.universidadadmision.produccion.service.PersonaService;
import com.universidadadmision.produccion.service.PostulanteRequisitosService;
import com.universidadadmision.produccion.service.PostulantesService;
import com.universidadadmision.produccion.service.RequisitoModalidadService;
import com.universidadadmision.produccion.service.RequisitosService;
import com.universidadadmision.produccion.service.VacantesService;

import jakarta.mail.MessagingException;

import org.springframework.http.MediaType;

@RestController
@CrossOrigin
@RequestMapping("/postulantes")
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

	@Autowired
	private PostulanteRequisitosService postulanterequisitoservice;

	@Autowired
	private RequisitoModalidadService requisitomodalidadservice;

	@Autowired
	private RequisitosService requisitosservice;

	@Autowired
	private EmailService emailService;

	@PostMapping("/nuevo")
	public ResponseEntity<?> NuevoPostulante(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Vacantes vacante = vacantesservice.findByPeriodoidAndSedeidAndCarreraid(postulanteDtor.getPeriodoid(),
				postulanteDtor.getSedeid(), postulanteDtor.getCarreraid());

		Vacantes segundaVacante = vacantesservice.findByPeriodoidAndSedeidAndCarreraid(postulanteDtor.getPeriodoid(),
				postulanteDtor.getSedeid(), postulanteDtor.getSegundacarreraid());
		if (vacante == null || segundaVacante == null) {
			response.put("resultado", 0);
			response.put("mensaje", "No Existe Vacantes Segun el Periodo, Sede y Carrera Seleccionado");
			response.put("dato", "");

			return ResponseEntity.ok(response);
		}

		Persona persona = personaservice.findByDocumento(postulanteDtor.getTipodocumentoid(),
				postulanteDtor.getNumerodocumento());
		Long idpersona = 0L;
		if (persona == null) {
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
			personanew.setDiscapacidad(postulanteDtor.isDiscapacidad());
			personanew.setCarnetconadis(postulanteDtor.getCarnetconadis());
			personanew.setEstado(true);
			personanew.prePersist();

			try {
				personaservice.save(personanew);
				idpersona = personanew.getId();
			} catch (Exception e) {
				response.put("resultado", 0);
				response.put("mensaje", "Error al Grabar Datos del Postulante : " + e.getMessage());
				response.put("dato", "");
				return ResponseEntity.ok(response);
			}
		} else {
			idpersona = persona.getId();

			List<Postulantes> postulantebus = postulanteservice.findpostulantevacante(idpersona, vacante.getId());
			if (!postulantebus.isEmpty()) {
				response.put("resultado", 0);
				response.put("mensaje", "Postulante ya Registrado");
				response.put("dato", "");
				return ResponseEntity.ok(response);
			}
		}

		Postulantes postulantenew = new Postulantes();
		postulantenew.setPersonaid(idpersona);
		postulantenew.setVacanteid(vacante.getId());
		// nueva vacante ID
		postulantenew.setSegundavacanteid(segundaVacante.getId());

		GeneralDto codpostulantedto = postulanteservice.generacodigo(postulanteDtor.getPeriodoid());
		Periodo periodo = periodoservice.findByid(postulanteDtor.getPeriodoid());
		String codpostulante = periodo.getAnio_semestre() + codpostulantedto.getCodigo();
		postulantenew.setCodigo(codpostulante);

		postulantenew.setGrupo_id(postulanteDtor.getGrupoid());
		postulantenew.setModalidad_ingreso_id(postulanteDtor.getModalidadid());
		postulantenew.setEstado_postulante("B");
		postulantenew.setEstado(true);
		postulantenew.prePersist();

		try {
			postulanteservice.save(postulantenew);
			List<PostulanteRequisitoDtoR> postreq = postulanteDtor.getRequisitos();

			for (PostulanteRequisitoDtoR requisitoslista : postreq) {
				PostulantesRequisitos postulanterequinew = new PostulantesRequisitos();
				postulanterequinew.setPostulanteid(postulantenew.getId());
				postulanterequinew.setRequisitomodalidadid(requisitoslista.getRequisitomodalidadid());
				postulanterequinew.setUrl(requisitoslista.getUrl());
				postulanterequinew.setRequisitovalidado("P"); // P = Pendiente, A = Aceptado, R = Rechazado
				postulanterequinew.setEstado(true);
				postulanterequisitoservice.save(postulanterequinew);
				// System.out.println(postulante.getCodigo());
			}

		} catch (Exception e) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al Grabar el Postulante : " + e.getMessage());
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		response.put("resultado", 1);
		response.put("mensaje", "Datos del Postulante grabados correctamente");
		response.put("dato", postulantenew);
		// response.put("dato","");

		return ResponseEntity.ok(response);
	}

	@PostMapping("/validapostulante")
	public ResponseEntity<?> ValidaPostulante(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		ValidaPostulanteDtoR valida = postulanteservice.validarPostulante(postulanteDtor.getTipodocumentoid(),
				postulanteDtor.getNumerodocumento(), postulanteDtor.getPeriodoid());
		if (valida != null) {
			response.put("resultado", 0);
			response.put("mensaje", "Postulante ya registrado");
			response.put("dato", valida);
			return ResponseEntity.ok(response);
		} else {
			response.put("resultado", 1);
			response.put("mensaje", "Postulante no registrado");
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}
	}

	@PostMapping("/obtenerdatos")
	public ResponseEntity<?> obtenerPostulante(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		PostulantesDto postulante = postulanteservice.obtenerPostulante(postulanteDtor.getId());

		if (postulante != null) {
			List<PostulanteRequisitoDto> requisitos = postulanteservice
					.obtenerRequisitoPostulante(postulanteDtor.getId());

			PostulantesDtoR postulanteDtoR = new PostulantesDtoR();
			postulanteDtoR.setId(postulante.getId());

			postulanteDtoR.setTipodocumentoid(postulante.getIdtipodoc());
			postulanteDtoR.setNumerodocumento(postulante.getnro_documento());
			postulanteDtoR.setNombre(postulante.getNombre());
			postulanteDtoR.setApellido_paterno(postulante.getApellido_paterno());
			postulanteDtoR.setApellido_materno(postulante.getApellido_materno());
			postulanteDtoR.setSexo(postulante.getSexo());
			postulanteDtoR.setEmail(postulante.getEmail());
			postulanteDtoR.setCelular(postulante.getCelular());
			postulanteDtoR.setTelefono(postulante.getTelefono());
			postulanteDtoR.setFecha_nacimiento(postulante.getFecha_nacimiento());
			postulanteDtoR.setDireccion(postulante.getDireccion());
			postulanteDtoR.setUbigeo_id(postulante.getUbigeo_id());
			postulanteDtoR.setGrupoid(postulante.getGrupo_id());
			postulanteDtoR.setModalidadid(postulante.getModalidad_ingreso_id());
			postulanteDtoR.setEstadopostulante(postulante.getEstado_postulante());
			//postulanteDtoR.setEstado(postulante.getEstado());
			postulanteDtoR.setPeriodoid(postulante.getid_periodo());
			postulanteDtoR.setSedeid(postulante.getid_sede());
			postulanteDtoR.setCarreraid(postulante.getid_carrera());
			postulanteDtoR.setSegundacarreraid(postulante.getsegunda_id_carrera());
			postulanteDtoR.setDiscapacidad(postulante.getDiscapacidad());
			postulanteDtoR.setCarnetconadis(postulante.getCarnetconadis());

			List<PostulanteRequisitoDtoR> requisitosDtoR = new ArrayList<>();
			for (PostulanteRequisitoDto requisito : requisitos) {
				PostulanteRequisitoDtoR requisitoDtoR = new PostulanteRequisitoDtoR();
				requisitoDtoR.setId(requisito.getId());
				requisitoDtoR.setRequisitomodalidadid(requisito.getRequisitomodalidadid());
				requisitoDtoR.setUrl(requisito.getUrl());
				requisitosDtoR.add(requisitoDtoR);
			}
			postulanteDtoR.setRequisitos(requisitosDtoR);

			// Preparar la respuesta
			response.put("resultado", 1);
			response.put("mensaje", "Postulante encontrado");
			response.put("dato", postulanteDtoR);
		} else {
			// Preparar la respuesta cuando no se encuentra el postulante
			response.put("resultado", 0);
			response.put("mensaje", "Postulante no encontrado");
			response.put("dato", "");
		}

		// Retornar la respuesta
		return ResponseEntity.ok(response);
	}

	@PostMapping("/lista")
	public ResponseEntity<?> ListaPostulante() throws Exception {
		List<PostulantesDto> postulantelista = postulanteservice.listartodos();
		return ResponseEntity.ok(postulantelista);
	}

	@PostMapping("/requisitos")
	public ResponseEntity<?> ListaPostulanteRequisitos(@RequestBody PostulanteNotasIDtoR postulante) throws Exception {
		List<PostulanteRequisitoDto> postulanterequisitolista = postulanterequisitoservice
				.listapostulanterequisito(postulante.getId());
		return ResponseEntity.ok(postulanterequisitolista);
	}

	@PostMapping("/postulantenotaso")
	public ResponseEntity<?> ListaPostulanteNotasO(@RequestBody GrupoDtoR grupodtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Periodo periodo = periodoservice.findByid(grupodtor.getPeriodo_id());
		if (periodo == null) {
			response.put("resultado", 0);
			response.put("mensaje", "Periodo Seleccionado no Existe");
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		List<PostulanteNotasDto> postulantesnotaso = postulanteservice.postulantenotaso(grupodtor.getPeriodo_id());
		return ResponseEntity.ok(postulantesnotaso);
	}

	@PostMapping("/postulantegrupo")
	public ResponseEntity<?> ListaPostulanteGrupo(@RequestBody GrupoDtoR grupodtor) throws Exception {
		// Map<String, Object> response = new HashMap<>();
		/*
		 * Periodo periodo = periodoservice.findByid(grupodtor.getPeriodo_id());
		 * if (periodo == null){
		 * response.put("resultado", 0);
		 * response.put("mensaje", "Periodo Seleccionado no Existe");
		 * response.put("dato","");
		 * return ResponseEntity.ok(response);
		 * }
		 */
		List<PostulanteGrupoDto> postulantesgrupo = postulanteservice.postulantegrupo(grupodtor.getId());

		return ResponseEntity.ok(postulantesgrupo);
	}

	@PostMapping("/postulantenotasi")
	public ResponseEntity<?> ListaPostulanteNotasI(@RequestBody List<PostulanteNotasIDtoR> postulantesi)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		postulanteservice.postulantenotasi(postulantesi);
		// Hacer Validaciones
		// Cual es el criterio segun la Nota para que el Alumno pase de ser Postulante a
		// ser Ingresante

		// Periodo periodo = periodoservice.findByid(grupodtor.getPeriodo_id());
		// if (periodo == null){
		response.put("resultado", 0);
		response.put("mensaje", "Proceso de Notas generado correctamente");
		response.put("dato", "");
		return ResponseEntity.ok(response);

		// }

		// List<PostulanteNotasDto> postulantesnotaso =
		// postulanteservice.postulantenotaso(grupodtor.getPeriodo_id());
		// return ResponseEntity.ok(postulantesnotaso);
	}

	@PostMapping("/edita")
	public ResponseEntity<?> EditaPostulante(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		Postulantes postulanteedita = postulanteservice.read(postulanteDtor.getId());
		if (postulanteedita == null) {
			response.put("resultado", 0);
			response.put("mensaje", "Postulante Seleccionado no se encuentra registrado");
			response.put("dato", "");

			return ResponseEntity.ok(response);
		}

		Vacantes vacante = vacantesservice.findByPeriodoidAndSedeidAndCarreraid(postulanteDtor.getPeriodoid(),
				postulanteDtor.getSedeid(), postulanteDtor.getCarreraid());
		if (vacante == null) {
			response.put("resultado", 0);
			response.put("mensaje", "No Existe Vacantes Segun el Periodo, Sede y Carrera Seleccionado");
			response.put("dato", "");

			return ResponseEntity.ok(response);
		}

		Persona personaedit = personaservice.read(postulanteedita.getPersonaid());
		// Mejorar y verificar si se modifica tipo y numero de doc nuevo ya existe
		if (personaedit != null) {
			personaedit.setNombre(postulanteDtor.getNombre());
			personaedit.setApellido_paterno(postulanteDtor.getApellido_paterno());
			personaedit.setApellido_materno(postulanteDtor.getApellido_materno());
			personaedit.setTipodocumentoid(postulanteDtor.getTipodocumentoid());
			personaedit.setNrodocumento(postulanteDtor.getNumerodocumento());
			personaedit.setSexo(postulanteDtor.getSexo());
			personaedit.setEmail(postulanteDtor.getEmail());
			personaedit.setCelular(postulanteDtor.getCelular());
			personaedit.setTelefono(postulanteDtor.getTelefono());
			personaedit.setFecha_nacimiento(postulanteDtor.getFecha_nacimiento());
			personaedit.setDireccion(postulanteDtor.getDireccion());
			personaedit.setUbigeo_id(postulanteDtor.getUbigeo_id());
			personaedit.setEstado(postulanteDtor.isEstado());
			personaedit.setDiscapacidad(postulanteDtor.isDiscapacidad());
			personaedit.setCarnetconadis(postulanteDtor.getCarnetconadis());
			personaedit.preUpdate();
			personaservice.save(personaedit);
		}

		postulanteedita.setVacanteid(vacante.getId());
		postulanteedita.setGrupo_id(postulanteDtor.getGrupoid());
		postulanteedita.setModalidad_ingreso_id(postulanteDtor.getModalidadid());
		postulanteedita.setEstado(postulanteDtor.isEstado());
		postulanteedita.preUpdate();

		try {

			postulanteservice.save(postulanteedita);

		} catch (Exception e) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al Grabar Postulante : " + e.getMessage());
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		response.put("resultado", 1);
		response.put("mensaje", "Datos de Postulante grabados correctamente");
		response.put("dato", postulanteedita);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/estadorequisito")
	public ResponseEntity<?> EstadoRequisitoPostulante(@RequestBody PostulanteRequisitoDtoR postulanteDtor)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		String to = "";
		String subject = "";
		String body = "";

		PostulantesRequisitos postulanterequisito = postulanterequisitoservice.read(postulanteDtor.getId());
		postulanterequisito.setRequisitovalidado(postulanteDtor.getEstado());

		if (postulanteDtor.getEstado().equals("R")) {
			postulanterequisito.setMensaje_rechazo(postulanteDtor.getMensaje_rechazo());
			// Enviar Correo con Mensaje de Rechazo del Reuisito
		}
		if (postulanteDtor.getEstado().equals("P")) {
			postulanterequisito.setUrl(postulanteDtor.getUrl());
		}

		postulanterequisitoservice.save(postulanterequisito);

		if (postulanteDtor.getEstado().equals("R")) {

			Postulantes postulante = postulanteservice.read(postulanterequisito.getPostulanteid());
			Persona persona = personaservice.read(postulante.getPersonaid());
			RequisitosModalidad reqmod = requisitomodalidadservice.read(postulanterequisito.getRequisitomodalidadid());
			Requisitos requisito = requisitosservice.read(reqmod.getRequisitoid());

			to = persona.getEmail();
			subject = "Requisito Rechazado - Proceso de Admisión UPP";

			body = "<!DOCTYPE html>" +
					"<html lang=\"es\">" +
					"<head>" +
					"<meta charset=\"UTF-8\">" +
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
					"<title>Requisito Rechazado</title>" +
					"</head>" +
					"<body style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 20px; background-color: #f4f4f4;\">"
					+
					"<div style=\"max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
					+
					"<div style=\"text-align: center; margin-bottom: 20px;\">" +
					"<img src=\"https://uapvirtual.uap.edu.pe:8443/archivos/55031722350534804.png\" alt=\"Logo Universidad Politécnica del Perú\" style=\"max-width: 150px; height: auto;\">"
					+
					"</div>" +
					"<h1 style=\"color: #4544a7; text-align: center;\">Requisito Rechazado</h1>" +
					"<p>Estimado Postulante,</p>" +
					"<p>Se le informa que el requisito <strong>" + requisito.getDescripcion()
					+ "</strong> ha sido rechazado por el siguiente motivo:</p>" +
					"<p><strong>" + postulanteDtor.getMensaje_rechazo() + "</strong></p>" +
					"<p>Identificado con documento de identidad número <strong>" + persona.getNrodocumento()
					+ "</strong>,</p>" +
					"<p>Por favor, subsane dicha observación y reenvíe la información en este mismo correo o de manera presencial en la Oficina de Admisión de la Universidad Politécnica del Perú.</p>"
					+
					"<div style=\"text-align: center; margin-top: 20px; font-size: 0.8em; color: #666;\">" +
					"Oficina de Admisión<br>" +
					"Universidad Politécnica del Perú" +
					"</div>" +
					"</div>" +
					"</body>" +
					"</html>";

			try {
				emailService.sendHtmlEmail(to, subject, body);
				// return "Email HTML enviado con éxito!";
			} catch (MessagingException e) {
				// return "Error al enviar el correo: " + e.getMessage();
			}
		}

		response.put("resultado", 1);
		response.put("mensaje", "Requisito Actualizado correctamente");
		response.put("dato", postulanterequisito);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/editaestado")
	public ResponseEntity<?> EditaPostulanteestado(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();
		String to = "";
		String subject = "";
		String body = "";

		Postulantes postulanteedita = postulanteservice.read(postulanteDtor.getId());
		postulanteedita.setEstado_postulante("P");
		postulanteservice.save(postulanteedita);
		response.put("resultado", 1);
		response.put("mensaje", "Estado Postulante apto para rendir examen");

		Persona persona = personaservice.read(postulanteedita.getPersonaid());

		to = persona.getEmail();
		subject = "Situación de Postulante - Proceso de Admisión UPP";
		PostulantesDto postpas = postulanteservice.PostulantePassword(postulanteDtor.getId());

		body = "<!DOCTYPE html>" +
				"<html lang=\"es\">" +
				"<head>" +
				"<meta charset=\"UTF-8\">" +
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
				"<title>Habilitación para Examen de Ingreso</title>" +
				"</head>" +
				"<body style=\"font-family: Arial, sans-serif; color: #333; line-height: 1.6; margin: 0; padding: 20px; background-color: #f4f4f4;\">"
				+
				"<div style=\"max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
				+
				"<div style=\"text-align: center; margin-bottom: 20px;\">" +
				"<img src=\"https://uapvirtual.uap.edu.pe:8443/archivos/55031722350534804.png\" alt=\"Logo Universidad Politécnica del Perú\" style=\"max-width: 150px; height: auto;\">"
				+
				"</div>" +
				"<h1 style=\"color: #4544a7; text-align: center;\">Habilitación para Examen de Ingreso</h1>" +
				"<p>Estimado Postulante,</p>" +
				"<p>" + persona.getApellido_paterno() + " " + persona.getApellido_materno() + " " + persona.getNombre()
				+ ", identificado con documento de identidad número <strong>" + persona.getNrodocumento()
				+ "</strong>,</p>" +
				"<p>Se le informa que usted ha quedado habilitado para rendir el examen de ingreso a la Universidad Politécnica del Perú. Se adjunta información sobre la ubicación y acceso:</p>"
				+
				"<p><strong>Lugar del Examen:</strong> Calle Pedro Ruiz 251 - Pueblo Libre - Lima</p>" +
				"<p><strong>Código de Postulante:</strong> " + postulanteedita.getCodigo() + "</p>" +
				"<p><strong>Password de Acceso:</strong> " + postpas.getPassword() + "</p>" +
				"<div style=\"text-align: center; margin-top: 20px; font-size: 0.8em; color: #666;\">" +
				"Oficina de Admisión<br>" +
				"Universidad Politécnica del Perú" +
				"</div>" +
				"</div>" +
				"</body>" +
				"</html>";

		try {
			emailService.sendHtmlEmail(to, subject, body);
			// return "Email HTML enviado con éxito!";
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}

		response.put("dato", postulanteedita);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/trasladoacademico")
	public ResponseEntity<?> TrasladoAcademicoPostulante(@RequestBody PostulantesDtoR postulanteDtor) throws Exception {
		Map<String, Object> response = new HashMap<>();

		MigraAcadDto migraacad = postulanteservice.executeMigraAcademico(postulanteDtor.getId());

		if (migraacad.getCodigo() == 0) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al realizar el Traslado de Postulantes : " + migraacad.getDescripcion());
			response.put("dato", migraacad);
		} else {
			response.put("resultado", 1);
			response.put("mensaje", "Traslado de Postulantes Ejecutado con éxito");
			response.put("dato", migraacad);
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/elimina")
	public ResponseEntity<?> EliminaPostulante(@RequestBody PostulantesDtoR postulanteDtor, BindingResult result)
			throws Exception {
		Map<String, Object> response = new HashMap<>();

		Postulantes postulanteselimina = postulanteservice.read(postulanteDtor.getId());

		if (postulanteselimina == null) {
			response.put("resultado", 0);
			response.put("mensaje", "No existe el Postulante");
			response.put("dato", "");

			return ResponseEntity.ok(response);
		}

		try {
			postulanteservice.delete(postulanteDtor.getId());
		} catch (Exception e) {
			response.put("resultado", 0);
			response.put("mensaje", "Error al Eliminar el Postulante : " + e.getMessage());
			response.put("dato", "");
			return ResponseEntity.ok(response);
		}

		response.put("resultado", 1);
		response.put("mensaje", "Postulante eliminado correctamente");
		response.put("dato", postulanteselimina);
		return ResponseEntity.ok(response);
	}
}
