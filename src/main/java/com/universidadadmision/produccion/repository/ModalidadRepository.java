package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.universidadadmision.produccion.dto.ModalidadDto;
import com.universidadadmision.produccion.entity.Modalidad;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ModalidadRepository extends JpaRepository<Modalidad, Long> {
	@Transactional(readOnly=true)
	@Query(value = "select a.id, a.nombre, a.tipo_ingreso_id,b.nombre as nombre_tipoingreso, a.estado_auditoria as estado \n"
			+ "   from Admision.modalidad a \n"
			+ "   inner join Admision.tipo_ingreso b on b.id =  a.tipo_ingreso_id \n"
			+ "   order by a.id", nativeQuery = true )
	
	public List<ModalidadDto> ListaModalidad();
}
