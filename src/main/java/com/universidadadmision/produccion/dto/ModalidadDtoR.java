package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class ModalidadDtoR implements Serializable  {
	private static final long serialVersionUID = 5926468583005190708L;

	private Long id;
	private String nombre;
	private Long tipo_ingreso_id;
	private boolean estado;
}
