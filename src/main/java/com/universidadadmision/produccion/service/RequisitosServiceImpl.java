package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.entity.Requisitos;
import com.universidadadmision.produccion.repository.RequisitosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequisitosServiceImpl implements RequisitosService {
	@Autowired
	private RequisitosRepository requisitosrep;

	@Override
	public Requisitos read(Long id) {
		return requisitosrep.findById(id).orElse(null);
	}

	@Override
	public Requisitos save(Requisitos requisitos) {
		return requisitosrep.save(requisitos);
	}

	@Override
	public void delete(Long id) {
		requisitosrep.deleteById(id);

	}

	@Override
	public List<Requisitos> list() {
		return requisitosrep.findAll();
	}

}
