package com.universidadadmision.produccion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.universidadadmision.produccion.entity.Ubigeo;

@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, Long>  {
	@Transactional(readOnly=true)
	@Query(value = "select id,ubigeo,departamento,provincia,distrito,localidad\r\n"
			+ "   from general.ubigeo\r\n"
			+ "   where provincia = '00' and distrito = '00'\r\n"
			+ "   order by localidad", nativeQuery = true )
	
	public List<Ubigeo> ListaDepartamento();
	
	@Transactional(readOnly=true)
	@Query(value = "select id,ubigeo,departamento,provincia,distrito,localidad\r\n"
			+ "   from general.ubigeo\r\n"
			+ "   where departamento = :departamento and provincia <> ('00') and distrito = '00'\r\n"
			+ "   order by localidad   \r\n"
			+ "", nativeQuery = true )
	
	public List<Ubigeo> ListaProvincia(String departamento);
	
	@Transactional(readOnly=true)
	@Query(value = "select id,ubigeo,departamento,provincia,distrito,localidad\r\n"
			+ "   from general.ubigeo\r\n"
			+ "   where departamento = :departamento and provincia = :provincia and distrito <> '00'\r\n"
			+ "   order by localidad", nativeQuery = true )
	
	public List<Ubigeo> ListaDistrito(String departamento, String provincia);
	
}
