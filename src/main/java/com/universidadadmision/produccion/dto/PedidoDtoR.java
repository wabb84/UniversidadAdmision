package com.universidadadmision.produccion.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PedidoDtoR implements Serializable {
	private static final long serialVersionUID = 5926468583005190708L;

	private Long iddocumento;
	private String nrodocumento;
	private LocalDateTime fechaCreacion;
}
