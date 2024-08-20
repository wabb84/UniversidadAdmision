package com.universidadadmision.produccion.service;

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
}
