package com.universidadadmision.produccion.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.entity.Ubigeo;
import com.universidadadmision.produccion.repository.UbigeoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UbigeoServiceImpl implements UbigeoService {
	@Autowired
	private UbigeoRepository ubigeorep;

	@Override
	public List<Ubigeo> listardepartamentos() {
		return ubigeorep.ListaDepartamento();
	}

	@Override
	public List<Ubigeo> listarprovincias(String iddepartamento) {
		return ubigeorep.ListaProvincia(iddepartamento);
	}

	@Override
	public List<Ubigeo> listardistritos(String iddepartamento, String provincia) {
		return ubigeorep.ListaDistrito(iddepartamento, provincia);
	}
}
