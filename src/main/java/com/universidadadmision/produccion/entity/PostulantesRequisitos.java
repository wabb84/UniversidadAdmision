package com.universidadadmision.produccion.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Postulantes_Requisitos", schema="Admision" )
public class PostulantesRequisitos extends Auditable<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	/*@Column(name="postulante_id")
	private Long postulanteid;
	
	@Column(name="requisito_modalidad_id")
	private Long requisitomodalidadid;*/
	
	@ManyToOne
    @JoinColumn(name = "postulante_id")
    private Postulantes postulante;
    
    @ManyToOne
    @JoinColumn(name = "requisito_modalidad_id")
    private RequisitosModalidad requisito;
	
	@Column(name="url")
	private String url;
	
	@Column(name="requisito_validado")
	private boolean requisitovalidado;	
	
	@Column(name="estado_auditoria")
	private boolean estado;	
}
