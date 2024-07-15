package com.universidadadmision.produccion.dto;

import lombok.Getter;

@Getter
public class VacantesDtoR {
	private static final long serialVersionUID = 5926468583004150728L;
	private Long id;
	private Long periodo_id;
	private Long sede_id;
	private Long carrera_id;
	private Long vacantes;
	private boolean estado;
}