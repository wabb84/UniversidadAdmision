package com.universidadadmision.produccion.service;

import java.util.List;
import com.universidadadmision.produccion.entity.Periodo;

public interface PeriodoService {
	public List<Periodo> listarperiodos(boolean estado, Long tipoperiodo);
	public Periodo findByid(Long periodoid);
}
