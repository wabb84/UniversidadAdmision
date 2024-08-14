package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.GrupoDto;
import com.universidadadmision.produccion.entity.Grupo;
import com.universidadadmision.produccion.entity.Periodo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	@Transactional(readOnly=true)
	@Query(value = "select a.id,a.nombre,a.periodo_id,b.nombre as nombreperiodo, a.tipo_ingreso_id,c.nombre as nombre_tipoingreso,a.fecha_inicio,a.fecha_fin,a.fecha_inicio_evaluacion,a.fecha_fin_evaluacion,\r\n"
			+ "   a.estado_auditoria as estado, a.puntaje_maximo as puntajemaximo, a.puntaje_aprobatorio as puntajeaprobatorio\r\n"
			+ "   from Admision.grupo a\r\n"
			+ "   inner join General.Periodo b on b.id = a.periodo_id\r\n"
			+ "   inner join Admision.tipo_ingreso c on c.id =  a.tipo_ingreso_id order by a.id", nativeQuery = true )
	
	public List<GrupoDto> ListaGrupo();
	
	public List<Grupo> findByPeriodoidAndTipoingresoid(Long periodoid, Long tipoingresoid);
}
