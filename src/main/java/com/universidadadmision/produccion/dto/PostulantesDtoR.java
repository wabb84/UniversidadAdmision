package com.universidadadmision.produccion.dto;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class PostulantesDtoR  implements Serializable {
	private static final long serialVersionUID = 5924468583005150707L;
	
	private Long id;
	private Long tipodocumentoid;
	private String numerodocumento;
	private Long vacanteid;
	private Long grupoid;
	private Long modalidadid;
	private String estadopostulante;
	private boolean estado;
}
