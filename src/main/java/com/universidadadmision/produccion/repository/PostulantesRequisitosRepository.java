package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.PostulanteRequisitoDto;
import com.universidadadmision.produccion.entity.PostulantesRequisitos;

@Repository
public interface PostulantesRequisitosRepository extends JpaRepository<PostulantesRequisitos, Long>  {
	@Transactional(readOnly=true)
	@Query(value = "select a.id, c.descripcion, a.url, a.requisito_validado\r\n"
			+ " from Admision.Postulantes_Requisitos a\r\n"
			+ " inner join Admision.Requisitos_Modalidad b on b.id = a.requisito_modalidad_id\r\n"
			+ " inner join Admision.requisitos c on c.id = requisito_id\r\n"
			+ " where a.postulante_id = :postulanteid \r\n", nativeQuery = true )
	
	public List<PostulanteRequisitoDto> PostulanteRequisito(Long postulanteid);
	
}
