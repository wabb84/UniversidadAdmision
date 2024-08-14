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
@Table(name = "Requisitos_Modalidad", schema="Admision" )
public class RequisitosModalidad extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="modalidad_id")
	private Long modalidadid;
	
	@Column(name="requisito_id")
	private Long requisitoid;
	
	@Column(name="tipo_requisito")
	private String tipo_requisito;
	
	@Column(name="estado_auditoria")
	private boolean estado;
	
	@Column(name="requisito_conadis")
	private boolean requisitoconadis;
}
