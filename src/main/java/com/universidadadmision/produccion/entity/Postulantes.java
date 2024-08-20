package com.universidadadmision.produccion.entity;

import java.io.Serializable;

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
@Table(name = "postulantes", schema="Admision" )
public class Postulantes extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="persona_id")
	private Long personaid;
	
	@Column(name="vacante_id")
	private Long vacanteid;
	
	@Column(name="codigo")
	private String codigo;
	
	@Column(name="grupo_id")
	private Long grupo_id;
	
	@Column(name="modalidad_ingreso_id")
	private Long modalidad_ingreso_id;
	
	@Column(name="nota")
	private Long nota;
	
	@Column(name="estado_postulante")
	private String estado_postulante;
	
	@Column(name="estado_auditoria")
	private boolean estado;
	
	/*@Column(name="password")
	private String password;*/

}
