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
@Table(name = "ubigeo", schema="General")
public class Ubigeo implements Serializable  {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="ubigeo")
	private String ubigeo;
	
	@Column(name="departamento")
	private String departamento;
	
	@Column(name="provincia")
	private String provincia;
	
	@Column(name="distrito")
	private String distrito;
	
	@Column(name="localidad")
	private String localidad;
	
}
