package com.universidadadmision.produccion.dto;

public interface PostulantesDto {
	Long getId();
	Long getPersonaid();
	String getTipodocumento();
	String getNombrepostulante();
	String getnro_documento();
	Long getVacante_id();
	String getCodigo();
	Long getGrupo_id();
	String getNombregrupo();
	Long getModalidad_ingreso_id();
	String getNombremodalidad();
	String getEstado_postulante();
	boolean getEstado();
}