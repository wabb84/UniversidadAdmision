package com.universidadadmision.produccion.dto;

import lombok.Getter;

@Getter
public class ReqModDtoR {
	private Long id;
	private Long modalidadid;
	private Long requisitoid;
	private String tipo_requisito;
	private boolean estado;
	private boolean requisitoconadis;
}
