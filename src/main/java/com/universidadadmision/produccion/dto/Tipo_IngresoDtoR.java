package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Tipo_IngresoDtoR  implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;

	private Long id;
	private String nombre;
	private boolean estado;
}