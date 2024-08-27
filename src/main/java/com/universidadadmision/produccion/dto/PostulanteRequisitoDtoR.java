package com.universidadadmision.produccion.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostulanteRequisitoDtoR implements Serializable {
	private static final long serialVersionUID = 5924468583005150707L;

	private Long id;
	private Long requisitomodalidadid;
	private String url;
	private String estado;
	private String mensaje_rechazo;
}
