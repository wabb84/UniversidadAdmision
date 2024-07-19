package com.universidadadmision.produccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.entity.Catalogo;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long>  {
	@Transactional(readOnly=true)
	@Query(value = "select id,descripcion,catalogo_tipo_id\r\n"
			+ "   from general.Catalogo where catalogo_tipo_id = :idtipocat order by descripcion", nativeQuery = true )
	
	public List<Catalogo> ListaCatalogo(Long idtipocat);
}