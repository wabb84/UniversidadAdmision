package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.RequisitoModalidadDto;
import com.universidadadmision.produccion.entity.RequisitosModalidad;

@Repository
public interface RequisitosModalidadRepository extends JpaRepository<RequisitosModalidad, Long>  {
	@Transactional(readOnly=true)
	@Query(value = "select a.requisito_id,a.tipo_requisito,b.descripcion\r\n"
			+ "   from Admision.Requisitos_Modalidad a\r\n"
			+ "   inner join Admision.Requisitos b on b.id = a.requisito_id\r\n"
			+ "   where a.modalidad_id = :modalidadid", nativeQuery = true )
	
	public List<RequisitoModalidadDto> ListadoRequisitosModadlida(Long modalidadid);
}
