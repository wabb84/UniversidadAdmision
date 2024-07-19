package com.universidadadmision.produccion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.universidadadmision.produccion.dto.CatalogoDtoR;
import com.universidadadmision.produccion.dto.ModalidadDtoR;
import com.universidadadmision.produccion.dto.RequisitoModalidadDto;
import com.universidadadmision.produccion.entity.Carrera;
import com.universidadadmision.produccion.entity.Catalogo;
import com.universidadadmision.produccion.entity.Periodo;
import com.universidadadmision.produccion.entity.Sede;
import com.universidadadmision.produccion.entity.Ubigeo;
import com.universidadadmision.produccion.service.CarreraService;
import com.universidadadmision.produccion.service.CatalogoService;
import com.universidadadmision.produccion.service.PeriodoService;
import com.universidadadmision.produccion.service.RequisitoModalidadService;
import com.universidadadmision.produccion.service.SedeService;
import com.universidadadmision.produccion.service.UbigeoService;

@Controller
@CrossOrigin
@RequestMapping ("/maestro")
@Validated
public class MaestroController {
	@Autowired
	private CatalogoService catalogoservice;
	
	@Autowired
	private UbigeoService ubigeoservice;
	
	@Autowired
	private PeriodoService periodoservice;
	
	@Autowired
	private SedeService sedeservice;
	
	@Autowired
	private CarreraService carreraservice;
	
	@Autowired
	private RequisitoModalidadService requisitomodalidadservice;
	
	@PostMapping("/tipodocumento")
	public ResponseEntity<?> ListaCatalogo(@RequestBody CatalogoDtoR catalogoDtor) throws Exception {
		List<Catalogo> catalolista = catalogoservice.listartodos(catalogoDtor.getCatalogotipoid());
		return ResponseEntity.ok(catalolista);
	}
	
	@PostMapping("/departamento")
	public ResponseEntity<?> ListaDepartamento() throws Exception {
		List<Ubigeo> departamentolista = ubigeoservice.listardepartamentos();
		return ResponseEntity.ok(departamentolista);
	}
	
	@PostMapping("/provincia")
	public ResponseEntity<?> ListaProvincia(@RequestBody CatalogoDtoR catalogoDtor) throws Exception {
		List<Ubigeo> provincialista = ubigeoservice.listarprovincias(catalogoDtor.getDepartamento());
		return ResponseEntity.ok(provincialista);
	}
	
	@PostMapping("/distrito")
	public ResponseEntity<?> ListaDistrito(@RequestBody CatalogoDtoR catalogoDtor) throws Exception {
		List<Ubigeo> distritolista = ubigeoservice.listardistritos(catalogoDtor.getDepartamento(), catalogoDtor.getProvincia());
		return ResponseEntity.ok(distritolista);
	}
	
	@PostMapping("/periodo")
	public ResponseEntity<?> ListaPeriodo() throws Exception {
		List<Periodo> periodolista = periodoservice.listarperiodos(true, 4L); 

		return ResponseEntity.ok(periodolista);
	}

	@PostMapping("/sede")
	public ResponseEntity<?> ListaSede() throws Exception {
		List<Sede> sedelista = sedeservice.listarsedes(true); 
		return ResponseEntity.ok(sedelista);
	}

	@PostMapping("/carrera")
	public ResponseEntity<?> ListaCarrera() throws Exception {
		List<Carrera> carreralista = carreraservice.listarcarreras(true); 

		return ResponseEntity.ok(carreralista);
	}
	
	@PostMapping("/modalidadrequisito")
	public ResponseEntity<?> ListaModalidadRequisito(@RequestBody ModalidadDtoR modalidadDtor) throws Exception {
		List<RequisitoModalidadDto> requisitomodalidadlista = requisitomodalidadservice.listarxrequisitomodalidad(modalidadDtor.getId()); 
		return ResponseEntity.ok(requisitomodalidadlista);
	}
}
