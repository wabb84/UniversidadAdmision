package com.universidadadmision.produccion.dto;

import java.time.LocalDate;

public interface PostulantesDto {
	Long getId();

	Long getPersonaid();

	Long getIdtipodoc();

	String getTipodocumento();

	String getApellido_paterno();

	String getApellido_materno();

	String getNombre();

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

	Long getid_periodo();

	Long getid_carrera();

	String getCarrera();

	Long getid_sede();

	String getSede();

	String getPassword();

	String getDepartamento();

	String getProvincia();

	String getDistrito();

	Boolean getDiscapacidad();

	String getCarnetconadis();
}