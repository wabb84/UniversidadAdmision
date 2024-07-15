package com.universidadadmision.produccion.dto;

import lombok.Getter;

@Getter
public class ModalidadDtoR {
	private static final long serialVersionUID = 5926468583005150708L;

	private Long id;
	private String nombre;
	private Long tipo_ingreso_id;
	private boolean estado;
}
