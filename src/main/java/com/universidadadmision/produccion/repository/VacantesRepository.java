package com.universidadadmision.produccion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.VacantesDto;
import com.universidadadmision.produccion.entity.Vacantes;

@Repository
public interface VacantesRepository  extends JpaRepository<Vacantes, Long> {
	@Transactional(readOnly=true)
	@Query(value = "select a.id,a.periodo_id,b.nombre as nombreperiodo,a.sede_id,c.nombre as nombresede,a.carrera_id, d.nombre as nombrecarrera, a.estado_auditoria as estado,\r\n"
			+ "   a.vacantes\r\n"
			+ "   from Admision.vacantes a\r\n"
			+ "   inner join  General.Periodo b on b.id = a.periodo_id\r\n"
			+ "   inner join  General.Sede c on c.id = a.sede_id\r\n"
			+ "   inner join  General.Carrera d on d.id = a.carrera_id\r\n"
			+ "   order by a.id", nativeQuery = true )
	
	public List<VacantesDto> ListaVacantes();
	
	//@Transactional(readOnly=true)
	//@Query(value = "select id from Admision.vacantes where periodo_id = :idperiodo and sede_id = :idsede and carrera_id = :idcarrera", nativeQuery = true )
	//@Query(value = "select id from Admision.vacantes where periodo_id = :idperiodo and sede_id = :idsede", nativeQuery = true )
	
	//public Vacantes ListaVacantesF(Long idperiodo, Long idsede, Long idcarrera);
	//public Vacantes ListaVacantesF(Long idperiodo, Long idsede, Long idcarrera);
	public Vacantes findByPeriodoidAndSedeidAndCarreraid(Long idperiodo, Long idsede, Long idcarrera);
}