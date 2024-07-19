package com.universidadadmision.produccion.dto;

import java.time.LocalDate;

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
	Long getSexo();
	String getDireccion();
	String getEmail();
	String getCelular();
	String getTelefono();
	Long getUbigeo_id();
	LocalDate getFecha_nacimiento();
}