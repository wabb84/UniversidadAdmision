package com.universidadadmision.produccion.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class PostulanteNotasIDtoR implements Serializable{
	private static final long serialVersionUID = 5924468983005150707L;
	
	private Long id;
	private String aniosemestre;
	private String codigo;
	private String codcarrera;
	private String codsede;
	private Long nota;
}
