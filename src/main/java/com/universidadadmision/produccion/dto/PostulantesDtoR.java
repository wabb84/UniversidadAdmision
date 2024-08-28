package com.universidadadmision.produccion.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostulantesDtoR implements Serializable {
	private static final long serialVersionUID = 5924468583005150707L;

	private Long id;
	private Long tipodocumentoid;
	private String numerodocumento;
	private String nombre;
	private String apellido_paterno;
	private String apellido_materno;
	private Long sexo;
	private String email;
	private String celular;
	private String telefono;
	private LocalDate fecha_nacimiento;
	private String direccion;
	private Long ubigeo_id;
	private Long grupoid;
	private Long modalidadid;
	private String estadopostulante;
	private boolean estado;
	private Long periodoid;
	private Long sedeid;
	private Long carreraid;
	private Long segundacarreraid;
	private boolean discapacidad;
	private String carnetconadis;

	private String departamento;
	private String provincia;
	private String distrito;

	private List<PostulanteRequisitoDtoR> requisitos;
}
