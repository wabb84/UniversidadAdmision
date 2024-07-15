package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.dto.PostulantesDto;
import com.universidadadmision.produccion.entity.Postulantes;

public interface PostulantesRepository extends JpaRepository<Postulantes, Long>  {
	@Transactional(readOnly=true)
	@Query(value = "select a.id, a.persona_id as personaid,c.abreviatura as tipodocumento, b.apellido_paterno +' '+ b.apellido_materno +' '+ b.nombre as nombrepostulante,b.nro_documento,\r\n"
			+ "       a.vacante_id,a.codigo,a.grupo_id,d.nombre as nombregrupo,a.modalidad_ingreso_id,e.nombre as nombremodalidad,a.estado_postulante,a.estado_auditoria as estado\r\n"
			+ "   from Admision.postulantes a\r\n"
			+ "      inner join general.persona b on b.id = a.persona_id\r\n"
			+ "	  inner join general.Catalogo c on c.id = b.cat_tipo_documento_id\r\n"
			+ "	  inner join Admision.grupo d on d.id = a.grupo_id\r\n"
			+ "	  inner join Admision.modalidad e on e.id = a.modalidad_ingreso_id\r\n"
			+ "   order by a.id", nativeQuery = true )
	
	public List<PostulantesDto> ListaGrupo();
}
