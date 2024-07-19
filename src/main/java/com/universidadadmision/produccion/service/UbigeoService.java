package com.universidadadmision.produccion.service;

import java.util.List;
import com.universidadadmision.produccion.entity.Ubigeo;

public interface UbigeoService {
	public List<Ubigeo> listardepartamentos();
	public List<Ubigeo> listarprovincias(String iddepartamento);
	public List<Ubigeo> listardistritos(String iddepartamento, String provincia);
}
