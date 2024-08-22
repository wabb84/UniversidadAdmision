package com.universidadadmision.produccion.dto;

import java.time.LocalDate;

public interface GrupoDto {
	Long getId();

	String getNombre();

	Long getPeriodo_id();

	String getNombreperiodo();

	Long getTipo_ingreso_id();

	String getNombre_tipoingreso();

	LocalDate getFecha_inicio();

	LocalDate getFecha_fin();

	LocalDate getFecha_inicio_evaluacion();

	LocalDate getFecha_fin_evaluacion();

	boolean getEstado();

	Long getPuntajemaximo();

	Long getPuntajeaprobatorio();

	boolean getCarganotas();
}
