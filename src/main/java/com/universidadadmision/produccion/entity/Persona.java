package com.universidadadmision.produccion.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "persona", schema="General" )
public class Persona  extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;

	@Column(name="cat_tipo_documento_id")
	private Long tipodocumentoid;

	@Column(name="nro_documento")
	private String nrodocumento;

	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido_paterno")
	private String apellido_paterno;
	
	@Column(name="apellido_materno")
	private String apellido_materno;
	
	@Column(name="sexo")
	private Long sexo;
	
	@Column(name="email")
	private String email;
	
	@Column(name="celular")
	private String celular;
	
	@Column(name="telefono")
	private String telefono;
	
	@JsonFormat( pattern="yyyy-MM-dd" )
	@Column(name="fecha_nacimiento")
    private LocalDate fecha_nacimiento;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="ubigeo_id")
	private Long ubigeo_id;
	
	@Column(name="estado_auditoria")
	private boolean estado;
	
}
