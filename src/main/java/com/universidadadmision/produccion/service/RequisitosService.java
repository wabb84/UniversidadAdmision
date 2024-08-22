package com.universidadadmision.produccion.service;

import java.util.List;

import com.universidadadmision.produccion.entity.Requisitos;

public interface RequisitosService {
	public Requisitos read(Long id);

	public Requisitos save(Requisitos requisitos);

	public void delete(Long id);

	public List<Requisitos> list();

}
