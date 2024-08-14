package com.universidadadmision.produccion.entity;

import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "grupo", schema="Admision" )
public class Grupo extends Auditable<String> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="periodo_id")
	private Long periodoid;
	
	@Column(name="tipo_ingreso_id")
	private Long tipoingresoid;
	
	@JsonFormat( pattern="yyyy-MM-dd" )
	@Column(name="fecha_inicio")
    private LocalDate fecha_inicio;
	
	@JsonFormat( pattern="yyyy-MM-dd" )
	@Column(name="fecha_fin")
    private LocalDate fecha_fin;

	@JsonFormat( pattern="yyyy-MM-dd" )
	@Column(name="fecha_inicio_evaluacion")
    private LocalDate fecha_inicio_evaluacion;

	@JsonFormat( pattern="yyyy-MM-dd" )
	@Column(name="fecha_fin_evaluacion")
    private LocalDate fecha_fin_evaluacion;

	@Column(name="estado_auditoria")
	private boolean estado;
	
	@Column(name="puntaje_maximo")
	private Long puntajemaximo;

	@Column(name="puntaje_aprobatorio")
	private Long puntajeaprobatorio;
}
