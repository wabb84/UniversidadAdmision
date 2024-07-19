package com.universidadadmision.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.universidadadmision.produccion.entity.Catalogo;
import com.universidadadmision.produccion.repository.CatalogoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {
	@Autowired
	private CatalogoRepository catalogorep;
	
	@Override
	public List<Catalogo> listartodos(long idcat) {
		return catalogorep.ListaCatalogo(idcat);
	}

}
