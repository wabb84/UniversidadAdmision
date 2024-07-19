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
@Table(name = "vacantes", schema="Admision" )
public class Vacantes extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="periodo_id")
	private Long periodoid;
	
	@Column(name="sede_id")
	private Long sedeid;

	@Column(name="carrera_id")
	private Long carreraid;
	
	@Column(name="vacantes")
	private Long vacantes;

	@Column(name="estado_auditoria")
	private boolean estado;
}
