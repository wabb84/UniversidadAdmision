package com.universidadadmision.produccion.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class GrupoDtoR {
	private static final long serialVersionUID = 5926468583005150728L;
	private Long id;
	private String nombre;
	private Long periodo_id;
	private Long tipo_ingreso_id;
	private LocalDate fecha_inicio;
	private LocalDate fecha_fin;
	private LocalDate fecha_inicio_evaluacion;
	private LocalDate fecha_fin_evaluacion;
	private boolean estado;
}
